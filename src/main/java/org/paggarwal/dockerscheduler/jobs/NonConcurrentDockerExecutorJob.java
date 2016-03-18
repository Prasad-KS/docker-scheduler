package org.paggarwal.dockerscheduler.jobs;

import org.quartz.DisallowConcurrentExecution;

/**
 * Created by paggarwal on 3/17/16.
 */
@DisallowConcurrentExecution
public class NonConcurrentDockerExecutorJob extends DockerExecutorJob {
}
