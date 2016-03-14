package org.paggarwal.dockerscheduler.jobs;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.exception.NotFoundException;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.core.command.LogContainerResultCallback;
import com.github.dockerjava.core.command.PullImageResultCallback;
import com.github.dockerjava.core.command.WaitContainerResultCallback;
import org.paggarwal.dockerscheduler.models.Task;
import org.paggarwal.dockerscheduler.service.db.EnvironmentVariableService;
import org.paggarwal.dockerscheduler.service.db.TaskService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.inject.Inject;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by paggarwal on 3/10/16.
 */
public class DockerExecutorJob implements Job {
    @Inject
    private DockerClient dockerClient;

    @Inject
    private com.spotify.docker.client.DockerClient spotifyDockerClient;

    @Inject
    private EnvironmentVariableService environmentVariableService;

    @Inject
    private TaskService taskService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Starting job");
        int taskId = 7;//context.getMergedJobDataMap().getIntFromString("TASK_ID");

        System.out.println(taskId);
        Optional<Task> taskOpt = taskService.get(taskId);
        System.out.println(taskOpt.isPresent());

        if(taskOpt.isPresent()) {
            String image = taskOpt.get().getImage();
            String command = taskOpt.get().getCommand();
            String name = taskOpt.get().getName();
            runDockerImage(image, command, name);
        }
        System.out.println("Finished");
    }


    private void runDockerImage(String image, String command, String name) {
        CreateContainerResponse response = null;

        try {

            response = dockerClient.createContainerCmd(image).withCmd(command).withName(name).withEnv(environmentVariableService.list().stream().map(environmentVariable -> environmentVariable.getName() + "=" + environmentVariable.getValue()).collect(Collectors.toList())).exec();
        } catch (NotFoundException e) {
            PullImageResultCallback callback = new PullImageResultCallback();
            dockerClient.pullImageCmd(image)..exec(callback);
            callback.awaitSuccess();
            response = dockerClient.createContainerCmd(image).withCmd(command).withName(name).withEnv(environmentVariableService.list().stream().map(environmentVariable -> environmentVariable.getName() + "=" + environmentVariable.getValue()).collect(Collectors.toList())).exec();
        }
        dockerClient.startContainerCmd(response.getId()).exec();
        dockerClient.logContainerCmd(response.getId()).withStdOut(true).withStdErr(true).withFollowStream(true).exec(new LogContainerResultCallback() {
            @Override
            public void onNext(Frame item) {
                System.out.println(item.toString());
            }
        });
        WaitContainerResultCallback waitContainerResultCallback = new WaitContainerResultCallback();
        dockerClient.waitContainerCmd(response.getId()).exec(waitContainerResultCallback);
        System.out.println(waitContainerResultCallback.awaitStatusCode());
        dockerClient.removeContainerCmd(response.getId()).exec();
    }
}
