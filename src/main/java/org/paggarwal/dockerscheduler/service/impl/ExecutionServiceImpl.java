package org.paggarwal.dockerscheduler.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.base.Throwables;
import org.iq80.snappy.Snappy;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.tools.StringUtils;
import org.paggarwal.dockerscheduler.models.Range;
import org.paggarwal.dockerscheduler.models.Execution;
import org.paggarwal.dockerscheduler.models.Task;
import org.paggarwal.dockerscheduler.service.ExecutionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.paggarwal.dockerscheduler.db.tables.Executions.EXECUTIONS;
import static org.paggarwal.dockerscheduler.db.tables.Tasks.TASKS;
import static org.paggarwal.dockerscheduler.models.Execution.Builder.anExecution;
import static org.paggarwal.dockerscheduler.models.Task.Builder.aTask;

/**
 * Created by paggarwal on 3/15/16.
 */
@Service
public class ExecutionServiceImpl implements ExecutionService {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Inject
    private DSLContext dsl;

    @Transactional(readOnly = true)
    @Override
    public int count(int taskId) {
        try {
            return dsl.selectCount().from(EXECUTIONS.join(TASKS)
                    .on(TASKS.ID.equal(EXECUTIONS.TASK_ID)))
                    .where(EXECUTIONS.TASK_ID.equal(taskId))
                    .fetchOne().value1();
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Execution> list(int taskId, Range range) {
        try {
            return dsl.selectFrom(EXECUTIONS.join(TASKS)
                    .on(TASKS.ID.equal(EXECUTIONS.TASK_ID)))
                    .where(EXECUTIONS.TASK_ID.equal(taskId))
                    .orderBy(EXECUTIONS.STARTED_ON.desc())
                    .limit(range.getEnd() - range.getStart() + 1)
                    .offset(range.getStart())
                    .fetch(this::mapRecord);
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteForTask(int taskId) {
        return dsl.deleteFrom(EXECUTIONS).where(EXECUTIONS.TASK_ID.equal(taskId)).execute() > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer create(Execution execution) {
        String envVars = execution.getEnvironmentVariables().entrySet().stream()
                .map(e -> e.getKey() + "=" + e.getValue()).collect(Collectors.joining("\n"));
        return dsl.insertInto(EXECUTIONS)
                .columns(EXECUTIONS.TASK_ID, EXECUTIONS.STATUS, EXECUTIONS.ENVIRONMENT_VARIABLES)
                .values((int) execution.getTask().getId(), execution.getStatus().ordinal(),envVars)
                .returning(EXECUTIONS.ID).fetchOne().getId();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean update(Execution execution) {
        return dsl.update(EXECUTIONS)
                .set(EXECUTIONS.STATUS, execution.getStatus().ordinal())
                .set(EXECUTIONS.STDOUT, Snappy.compress(execution.getStdout().getBytes(Charsets.UTF_8)))
                .set(EXECUTIONS.STDERR, Snappy.compress(execution.getStderr().getBytes(Charsets.UTF_8)))
                .set(EXECUTIONS.ENDED_ON, new Timestamp(execution.getEndedOn().getTime()))
                .where(EXECUTIONS.ID.equal((int)execution.getId()))
                .execute() > 0;
    }

    private Execution mapRecord(Record record) {
        try {
            Execution.Builder builder = anExecution()
                    .withId(record.getValue(EXECUTIONS.ID))
                    .withEndedOn(record.getValue(EXECUTIONS.ENDED_ON))
                    .withStartedOn(record.getValue(EXECUTIONS.STARTED_ON))
                    .withStderr(new String(Snappy.uncompress(record.getValue(EXECUTIONS.STDERR),0,record.getValue(EXECUTIONS.STDERR).length),Charsets.UTF_8))
                    .withStdout(new String(Snappy.uncompress(record.getValue(EXECUTIONS.STDOUT),0,record.getValue(EXECUTIONS.STDOUT).length),Charsets.UTF_8))
                    .withStatus(Execution.Status.values()[record.getValue(EXECUTIONS.STATUS)])
                    .withTask(aTask()
                            .withId(record.getValue(TASKS.ID))
                            .withImage(record.getValue(TASKS.IMAGE))
                            .withName(record.getValue(TASKS.NAME))
                            .withCommand(record.getValue(TASKS.COMMAND))
                            .withCron(record.getValue(TASKS.CRON))
                            .withCreatedOn(record.getValue(TASKS.CREATED_ON))
                            .withSuccess(record.getValue(TASKS.SUCCESS))
                            .withFailed(record.getValue(TASKS.FAILED))
                            .withType(Task.Type.values()[record.getValue(TASKS.TYPE)])
                            .build());
            if(!StringUtils.isBlank(record.getValue(EXECUTIONS.ENVIRONMENT_VARIABLES))) {
                builder.withEnvironmentVariables(OBJECT_MAPPER.readValue(record.getValue(EXECUTIONS.ENVIRONMENT_VARIABLES), Map.class));
            }

            return builder.build();
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }
}
