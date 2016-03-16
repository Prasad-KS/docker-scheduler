package org.paggarwal.dockerscheduler.jobs;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.exception.NotFoundException;
import com.github.dockerjava.core.command.PullImageResultCallback;
import com.github.dockerjava.core.command.WaitContainerResultCallback;
import org.paggarwal.dockerscheduler.models.EnvironmentVariable;
import org.paggarwal.dockerscheduler.models.Execution;
import org.paggarwal.dockerscheduler.models.Task;
import org.paggarwal.dockerscheduler.service.EnvironmentVariableService;
import org.paggarwal.dockerscheduler.service.ExecutionService;
import org.paggarwal.dockerscheduler.service.TaskService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.paggarwal.dockerscheduler.models.Execution.Builder.anExecution;

/**
 * Created by paggarwal on 3/10/16.
 */
public class DockerExecutorJob implements Job {
    @Inject
    private DockerClient dockerClient;

    @Inject
    private EnvironmentVariableService environmentVariableService;

    @Inject
    private TaskService taskService;

    @Inject
    private ExecutionService executionService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LogHandler logHandler = new LogHandler();
        int taskId = context.getMergedJobDataMap().getIntFromString("TASK_ID");

        System.out.println(taskId);
        Optional<Task> taskOpt = taskService.get(taskId);
        System.out.println(taskOpt.isPresent());

        if(taskOpt.isPresent()) {
            List<EnvironmentVariable> environmentVariables = environmentVariableService.list();
            Execution.Builder builder = anExecution()
                    .withEnvironmentVariables(environmentVariables.stream()
                            .collect(Collectors.toMap(EnvironmentVariable::getName,EnvironmentVariable::getValue)))
                    .withStatus(Execution.Status.EXECUTING)
                    .withTask(taskOpt.get())
                    .but();
            builder.withId(executionService.create(builder.build()));

            String image = taskOpt.get().getImage();
            String command = taskOpt.get().getCommand();
            String name = taskOpt.get().getName();
            builder.withStatus(Execution.Status.FAILED);
            try {
                if(runDockerImage(image, command, name, environmentVariables, logHandler) == 0) {
                    builder.withStatus(Execution.Status.SUCCEDED);
                }
            } catch (Exception e) {
                //
            }
            builder.withEndedOn(new Date());
            executionService.update(builder.build());
        }
    }


    private Integer runDockerImage(String image, String command, String name, List<EnvironmentVariable> environmentVariables, LogHandler logHandler) {
        CreateContainerResponse response = null;

        try {
            response = dockerClient.createContainerCmd(image).withCmd(command).withName(name).withEnv(environmentVariables.stream().map(environmentVariable -> environmentVariable.getName() + "=" + environmentVariable.getValue()).collect(Collectors.toList())).exec();
        } catch (NotFoundException e) {
            PullImageResultCallback callback = new PullImageResultCallback();
            dockerClient.pullImageCmd(image).exec(callback);
            callback.awaitSuccess();
            response = dockerClient.createContainerCmd(image).withCmd(command).withName(name).withEnv(environmentVariables.stream().map(environmentVariable -> environmentVariable.getName() + "=" + environmentVariable.getValue()).collect(Collectors.toList())).exec();
        }
        dockerClient.startContainerCmd(response.getId()).exec();
        dockerClient.logContainerCmd(response.getId()).withStdOut(true).withStdErr(true).withFollowStream(true).exec(logHandler);
        WaitContainerResultCallback waitContainerResultCallback = new WaitContainerResultCallback();
        dockerClient.waitContainerCmd(response.getId()).exec(waitContainerResultCallback);

        dockerClient.removeContainerCmd(response.getId()).exec();
        return waitContainerResultCallback.awaitStatusCode();
    }
}
