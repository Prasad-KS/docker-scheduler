package org.paggarwal.dockerscheduler.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.paggarwal.dockerscheduler.models.Execution;
import org.paggarwal.dockerscheduler.models.Task;
import org.paggarwal.dockerscheduler.service.ExecutionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.paggarwal.dockerscheduler.generated.tables.Executions.EXECUTIONS;
import static org.paggarwal.dockerscheduler.generated.tables.Tasks.TASKS;
import static org.paggarwal.dockerscheduler.models.Execution.Builder.anExecution;
import static org.paggarwal.dockerscheduler.models.Task.Builder.aTask;

/**
 * Created by paggarwal on 3/15/16.
 */
@Service
public class ExecutionServiceImpl implements ExecutionService {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Inject
    private DSL dsl;

    @Transactional(readOnly = true)
    @Override
    public List<Execution> list(int taskId) {
        try {
            return dsl.selectFrom(EXECUTIONS.join(TASKS)
                    .on(TASKS.ID.equal(EXECUTIONS.TASK_ID)))
                    .where(EXECUTIONS.TASK_ID.equal(taskId))
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
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean update(Execution execution) {
        return false;
    }

    private Execution mapRecord(Record record) {
        try {
            return anExecution()
                    .withId(record.getValue(EXECUTIONS.ID))
                    .withEndedOn(record.getValue(EXECUTIONS.ENDED_ON))
                    .withStartedOn(record.getValue(EXECUTIONS.STARTED_ON))
                    .withStderr(record.getValue(EXECUTIONS.STDERR))
                    .withStdout(record.getValue(EXECUTIONS.STDOUT))
                    .withStatus(Execution.Status.values()[record.getValue(EXECUTIONS.STATUS)])
                    .withEnvironmentVariables(OBJECT_MAPPER.readValue(record.getValue(EXECUTIONS.ENVIRONMENT_VARIABLES), Map.class))
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
                            .build())
                    .build();
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }
}
