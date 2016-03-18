package org.paggarwal.dockerscheduler.service.impl;

import com.google.common.base.Throwables;
import org.jooq.DSLContext;
import org.paggarwal.dockerscheduler.generated.tables.records.TasksRecord;
import org.paggarwal.dockerscheduler.jobs.DockerExecutorJob;
import org.paggarwal.dockerscheduler.jobs.NonConcurrentDockerExecutorJob;
import org.paggarwal.dockerscheduler.models.Task;
import org.paggarwal.dockerscheduler.models.TaskGroup;
import org.paggarwal.dockerscheduler.service.ExecutionService;
import org.paggarwal.dockerscheduler.service.TaskService;
import org.quartz.*;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.paggarwal.dockerscheduler.generated.tables.Tasks.TASKS;
import static org.paggarwal.dockerscheduler.models.Task.*;

/**
 * Created by paggarwal on 3/2/16.
 */
@Service
public class TaskServiceImpl implements TaskService {
    @Inject
    private DSLContext dsl;

    @Inject
    private Scheduler scheduler;

    @Inject
    private ExecutionService executionService;

    @Transactional(readOnly = true)
    public List<Task> listTasks() {
        return dsl.selectFrom(TASKS).where(TASKS.TYPE.equal(Type.TASK.ordinal())).fetch(this::mapRecord);
    }

    @Transactional(readOnly = true)
    public List<Task> listScheduledTasks() {
        return dsl.selectFrom(TASKS).where(TASKS.TYPE.equal(Type.SCHEDULED_TASK.ordinal())).fetch(this::mapRecord);
    }

    @Transactional(readOnly = true)
    public Optional<Task> get(int id) {
        return dsl.selectFrom(TASKS).where(TASKS.ID.equal(id)).fetch(this::mapRecord).stream().findFirst();
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public Integer create(Task task) {
        try {
            int id;

            id = dsl.insertInto(TASKS).columns(TASKS.NAME , TASKS.IMAGE, TASKS.COMMAND, TASKS.CRON, TASKS.TYPE)
                    .values(task.getName(), task.getImage(), task.getCommand(),
                            task.getCron(), task.getType().ordinal()).returning(TASKS.ID).fetchOne().getId();

            TaskGroup taskGroup = task.getType() == Type.SCHEDULED_TASK ? TaskGroup.SCHEDULED_TASK : TaskGroup.TASK;

            JobBuilder jobBuilder = JobBuilder.newJob()
                    .usingJobData("TASK_ID", Integer.toString(id))
                    .requestRecovery()
                    .storeDurably()
                    .withIdentity(Integer.toString(id), taskGroup.name());

            if (task.getType() == Type.SCHEDULED_TASK) {
                jobBuilder.ofType(NonConcurrentDockerExecutorJob.class);
                CronTriggerImpl trigger = new CronTriggerImpl();
                trigger.setCronExpression(task.getCron());
                trigger.setKey(TriggerKey.triggerKey(Integer.toString(id), TaskGroup.SCHEDULED_TASK.toString()));
                scheduler.scheduleJob(jobBuilder.build(), trigger);
            } else {
                jobBuilder.ofType(DockerExecutorJob.class);
                scheduler.addJob(jobBuilder.build(), true);
            }

            return id;

        } catch(Exception e) {
            throw Throwables.propagate(e);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean delete(int id) {
        try {
            executionService.deleteForTask(id);
            boolean deleted = dsl.delete(TASKS).where(TASKS.ID.equal(id)).execute() > 0;
            scheduler.deleteJobs(Arrays.asList(JobKey.jobKey(Integer.toString(id), TaskGroup.SCHEDULED_TASK.toString()), JobKey.jobKey(Integer.toString(id), TaskGroup.TASK.toString())));
            return deleted;
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }

    private Task mapRecord(TasksRecord record) {
        return Builder.aTask()
                .withId(record.getId())
                .withImage(record.getImage())
                .withName(record.getName())
                .withCommand(record.getCommand())
                .withCron(record.getCron())
                .withCreatedOn(record.getCreatedOn())
                .withSuccess(record.getSuccess())
                .withFailed(record.getFailed())
                .withType(Task.Type.values()[record.getType()])
                .build();
    }
}
