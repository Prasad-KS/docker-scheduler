package org.paggarwal.dockerscheduler.jobs;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.exception.NotFoundException;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.core.command.LogContainerResultCallback;
import com.github.dockerjava.core.command.PullImageResultCallback;
import com.github.dockerjava.core.command.WaitContainerResultCallback;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.inject.Inject;

/**
 * Created by paggarwal on 3/10/16.
 */
public class DockerExecutorJob implements Job {
    @Inject
    private DockerClient dockerClient;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        CreateContainerResponse response = null;
        try {
            response = dockerClient.createContainerCmd("ubuntu").withCmd("env").withName("test").withEnv("HELP_ME=Hello World").
                    exec();
        } catch (NotFoundException e) {
            PullImageResultCallback callback = new PullImageResultCallback();
            dockerClient.pullImageCmd("ubuntu:latest").exec(callback);
            callback.awaitSuccess();
            response = dockerClient.createContainerCmd("ubuntu").withCmd("env").withName("test").withEnv("HELP_ME=Hello World").
                    exec();
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
        System.out.println("Finished");
    }
}
