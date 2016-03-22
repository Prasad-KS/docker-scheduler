package org.paggarwal.dockerscheduler.jobs;

import org.paggarwal.dockerscheduler.service.ExecutionService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.inject.Inject;

/**
 * Created by paggarwal on 3/21/16.
 */
@DisallowConcurrentExecution
public class ExecutionCleanupJob implements Job {
    @Inject
    private ExecutionService executionService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        executionService.deleteTasksOlderThan(System.currentTimeMillis() - 24*60*60*1000);
    }
}
