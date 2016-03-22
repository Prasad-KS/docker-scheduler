package org.paggarwal.dockerscheduler.jobs;

import com.auth0.jwt.internal.com.fasterxml.jackson.core.type.TypeReference;
import com.auth0.jwt.internal.com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.exception.NotFoundException;
import com.github.dockerjava.core.command.PullImageResultCallback;
import com.github.dockerjava.core.command.WaitContainerResultCallback;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.iq80.snappy.Snappy;
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
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.paggarwal.dockerscheduler.models.Execution.Builder.anExecution;

/**
 * Created by paggarwal on 3/10/16.
 */
public class DockerExecutorJob implements Job {
    public static final String TASK_ID = "TASK_ID";
    public static final String PAYLOAD = "PAYLOAD";
    public static final String TASK_NAME_SEPARATOR = "_";
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

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
        int taskId = context.getMergedJobDataMap().getIntFromString(TASK_ID);
        Optional<String> payloadOpt = Optional.ofNullable(context.getMergedJobDataMap().getString(PAYLOAD));
        Optional<Task> taskOpt = taskService.get(taskId);

        if(taskOpt.isPresent()) {
            List<EnvironmentVariable> environmentVariables = environmentVariableService.list();
            Execution.Builder builder = anExecution()
                    .withEnvironmentVariables(environmentVariables.stream()
                            .collect(Collectors.toMap(EnvironmentVariable::getName,EnvironmentVariable::getValue)))
                    .withStatus(Execution.Status.EXECUTING)
                    .withTask(taskOpt.get())
                    .but();
            if(payloadOpt.isPresent()) {
                builder.withPayload(payloadOpt.get());
            }
            builder.withId(executionService.create(builder.build()));

            String image = taskOpt.get().getImage();
            List<String> command = Lists.newArrayList(taskOpt.get().getCommand());
            if(payloadOpt.isPresent()) {
                try {
                    command.addAll(OBJECT_MAPPER.readValue(payloadOpt.get(), new TypeReference<List<String>>() {}));
                } catch (IOException e) {
                    throw new JobExecutionException(e);
                }
            }
            String name = taskOpt.get().getName() + TASK_NAME_SEPARATOR + context.getFireInstanceId();
            Execution.Status status = Execution.Status.FAILED;
            String stdOut = "";
            String stdError = "";
            try {
                if(runDockerImage(image, command, name, environmentVariables, logHandler) == 0) {
                    status = Execution.Status.SUCCEDED;
                }
                stdError = logHandler.getStdErr();
            } catch (Exception e) {
                stdError = e.getMessage() + "\n" + Joiner.on("\n").join(e.getStackTrace());
            }
            stdOut = logHandler.getStdOut();
            executionService.update(builder
                    .withEndedOn(new Date())
                    .withStderr(stdError)
                    .withStdout(stdOut)
                    .withStatus(status)
                    .build());
        }
    }


    private Integer runDockerImage(String image, List<String> command, String name, List<EnvironmentVariable> environmentVariables, LogHandler logHandler) {
        String containerId = null;

        List<String> environmentVars = environmentVariables.stream().map(environmentVariable -> environmentVariable.getName() + "=" + environmentVariable.getValue()).collect(Collectors.toList());

        CreateContainerCmd createContainerCmd = dockerClient.createContainerCmd(image).withCmd(command).withName(name).withEnv(environmentVars);

        try {
            containerId = createContainerCmd.exec().getId();
        } catch (NotFoundException e) {
            PullImageResultCallback callback = new PullImageResultCallback();
            dockerClient.pullImageCmd(image).exec(callback);
            callback.awaitSuccess();
            containerId = createContainerCmd.exec().getId();
        }
        dockerClient.startContainerCmd(containerId).exec();
        dockerClient.logContainerCmd(containerId)
                .withStdOut(true)
                .withStdErr(true)
                .withFollowStream(true)
                .withTailAll()
                .withTimestamps(true).exec(logHandler);
        WaitContainerResultCallback waitContainerResultCallback = new WaitContainerResultCallback();
        dockerClient.waitContainerCmd(containerId).exec(waitContainerResultCallback);

        dockerClient.removeContainerCmd(containerId).withForce(true).exec();
        return waitContainerResultCallback.awaitStatusCode();
    }
}
