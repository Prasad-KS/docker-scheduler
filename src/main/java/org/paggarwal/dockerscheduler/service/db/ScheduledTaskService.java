package org.paggarwal.dockerscheduler.service.db;

import org.jooq.DSLContext;
import org.jooq.Record1;
import org.paggarwal.dockerscheduler.models.ScheduledTask;
import org.paggarwal.dockerscheduler.models.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

import static org.paggarwal.dockerscheduler.generated.tables.Tasks.TASKS;
import static org.paggarwal.dockerscheduler.generated.tables.ScheduledTasks.SCHEDULED_TASKS;

/**
 * Created by paggarwal on 3/2/16.
 */
@Service
public class ScheduledTaskService {

    @Inject
    private DSLContext dsl;

    @Transactional(readOnly = true)
    public List<ScheduledTask> list() {
        return dsl.selectFrom(TASKS.join(SCHEDULED_TASKS).on(SCHEDULED_TASKS.TASK_ID.equal(TASKS.ID)))
                .fetch(record -> ScheduledTask.Builder.aScheduledTask()
                        .withCron(record.getValue(SCHEDULED_TASKS.CRON))
                        .withId(record.getValue(SCHEDULED_TASKS.ID))
                        .withTask(Task.Builder.aTask()
                                .withId(record.getValue(TASKS.ID))
                                .withCommand(record.getValue(TASKS.COMMAND))
                                .withCreatedOn(record.getValue(TASKS.CREATED_ON))
                                .withImage(record.getValue(TASKS.IMAGE))
                                .withName(record.getValue(TASKS.NAME))
                                .withType(record.getValue(TASKS.TYPE))
                                .withSuccess(record.getValue(TASKS.SUCCESS))
                                .withFailed(record.getValue(TASKS.FAILED))
                                .build())
                        .build());
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer create(ScheduledTask scheduledTask) {
        int taskId = dsl.insertInto(TASKS).columns(TASKS.NAME
                , TASKS.IMAGE, TASKS.COMMAND, TASKS.TYPE).values(scheduledTask.getTask().getName(), scheduledTask.getTask().getImage(), scheduledTask.getTask().getCommand(),scheduledTask.getTask().getType()).returning(TASKS.ID).fetchOne().getId();
        return dsl.insertInto(SCHEDULED_TASKS).columns(SCHEDULED_TASKS.TASK_ID, SCHEDULED_TASKS.CRON).values(taskId, scheduledTask.getCron()).returning(SCHEDULED_TASKS.ID).fetchOne().getId();
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean delete(int id) {
        Optional<Record1<Integer>> taskIdOptional = dsl.select(SCHEDULED_TASKS.TASK_ID).from(SCHEDULED_TASKS).where(SCHEDULED_TASKS.ID.equal(id)).fetchOptional();
        if (taskIdOptional.isPresent()) {
            dsl.delete(SCHEDULED_TASKS).where(SCHEDULED_TASKS.ID.equal(id)).execute();
            dsl.delete(TASKS).where(TASKS.ID.equal(taskIdOptional.get().getValue(SCHEDULED_TASKS.TASK_ID))).execute();
        }
        return taskIdOptional.isPresent();
    }
}
