package org.paggarwal.dockerscheduler.service;

import com.google.common.base.Throwables;
import org.jooq.Record1;
import org.paggarwal.dockerscheduler.jobs.DockerExecutorJob;
import org.paggarwal.dockerscheduler.models.ScheduledTask;
import org.paggarwal.dockerscheduler.models.Task;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.TriggerKey;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

import static org.paggarwal.dockerscheduler.generated.tables.ScheduledTasks.SCHEDULED_TASKS;
import static org.paggarwal.dockerscheduler.generated.tables.Tasks.TASKS;

/**
 * Created by paggarwal on 3/14/16.
 */
public interface ScheduledTaskService {
    List<ScheduledTask> list();

    Integer create(ScheduledTask scheduledTask);

    boolean delete(int id);
}
