package org.paggarwal.dockerscheduler.jobs;

import org.paggarwal.dockerscheduler.service.ExecutionService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Inject;
import java.util.concurrent.TimeUnit;

/**
 * Created by paggarwal on 3/21/16.
 */
@DisallowConcurrentExecution
public class ExecutionCleanupJob implements Job {
    @Inject
    private ExecutionService executionService;

    @Value("#{ systemEnvironment['EXECUTION_RETENTION_DAYS'] ?: 1  }")
    private Integer executionRetentionInDays;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        executionService.deleteTasksOlderThan(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(executionRetentionInDays));
    }
}
