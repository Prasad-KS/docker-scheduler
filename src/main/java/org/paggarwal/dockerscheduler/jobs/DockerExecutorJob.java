package org.paggarwal.dockerscheduler.jobs;

import static com.github.dockerjava.api.model.HostConfig.newHostConfig;
import static com.google.common.collect.ImmutableMap.of;
import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.paggarwal.dockerscheduler.models.Execution.Builder.anExecution;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.kamranzafar.jtar.TarEntry;
import org.kamranzafar.jtar.TarOutputStream;
import org.paggarwal.dockerscheduler.models.EnvironmentVariable;
import org.paggarwal.dockerscheduler.models.Execution;
import org.paggarwal.dockerscheduler.models.Task;
import org.paggarwal.dockerscheduler.service.EnvironmentVariableService;
import org.paggarwal.dockerscheduler.service.ExecutionService;
import org.paggarwal.dockerscheduler.service.TaskService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Value;

import com.auth0.jwt.internal.com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.core.command.PullImageResultCallback;
import com.github.dockerjava.core.command.WaitContainerResultCallback;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

/**
 * Created by paggarwal on 3/10/16.
 */
public class DockerExecutorJob implements Job {
	public static final String TASK_ID = "TASK_ID";
	public static final String PAYLOAD = "PAYLOAD";
	public static final String TASK_NAME_SEPARATOR = "_";
	public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	@Value("#{ systemEnvironment['DOCKER_DNS'] }")
	private String dns;

	@Value("#{ systemEnvironment['DOCKER_DNS_SEARCH'] }")
	private String dnsSearch;

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
		LogHandler logHandler = new LogHandler() {
			@Override
			public void onComplete() {
				super.onComplete();
			}
		};
		int taskId = context.getMergedJobDataMap().getIntFromString(TASK_ID);
		Optional<String> payloadOpt = Optional.ofNullable(context.getMergedJobDataMap().getString(PAYLOAD));
		Optional<Task> taskOpt = taskService.get(taskId);

		if (taskOpt.isPresent()) {
			List<EnvironmentVariable> environmentVariables = environmentVariableService.list();
			Execution.Builder builder = anExecution()
					.withEnvironmentVariables(environmentVariables.stream()
							.collect(Collectors.toMap(EnvironmentVariable::getName, EnvironmentVariable::getValue)))
					.withStatus(Execution.Status.EXECUTING).withTask(taskOpt.get()).but();
			if (payloadOpt.isPresent()) {
				builder.withPayload(payloadOpt.get());
			}
			builder.withId(executionService.create(builder.build()));

			String image = taskOpt.get().getImage();
			String command = taskOpt.get().getCommand();
			String payload = payloadOpt.orElse(null);
			String name = taskOpt.get().getName() + TASK_NAME_SEPARATOR + context.getFireInstanceId();
			Execution.Status status = Execution.Status.FAILED;
			String stdOut = "";
			String stdError = "";
			try {
				System.out.println("STARTING");
				if (runDockerImage(image, command, name, environmentVariables, payload, logHandler) == 0) {
					status = Execution.Status.SUCCEDED;
					System.out.println("SUCCESS");
				} else {
					System.out.println("FAILED due to exception inside container");
				}
				stdError = logHandler.getStdErr();
				System.out.println(stdError);
			} catch (Exception e) {
				stdError = e.getMessage() + "\n" + Joiner.on("\n").join(e.getStackTrace());
				System.out.println("FAILED due to exception");
			}
			stdOut = logHandler.getStdOut();
			executionService.update(
					builder.withEndedOn(new Date()).withStderr(stdError).withStdout(stdOut).withStatus(status).build());
		}
	}

	private Integer runDockerImage(String image, String command, String name,
			List<EnvironmentVariable> environmentVariables, String payload, LogHandler logHandler)
			throws JobExecutionException, InterruptedException {

		PullImageResultCallback callback = new PullImageResultCallback();
		dockerClient.pullImageCmd(image).exec(callback).awaitCompletion();

		List<String> environmentVars = environmentVariables.stream()
				.map(environmentVariable -> environmentVariable.getName() + "=" + environmentVariable.getValue())
				.collect(Collectors.toList());
		CreateContainerCmd createContainerCmd = dockerClient.createContainerCmd(image).withCmd(command).withName(name)
				.withEnv(environmentVars).withLabels(of("io.rancher.container.network", "true"));

		if (isNotBlank(dns)) {
			createContainerCmd.withHostConfig(newHostConfig().withDns(Splitter.on(",").splitToList(dns)));
		}

		if (isNotBlank(dnsSearch)) {
			createContainerCmd.withHostConfig(newHostConfig().withDnsSearch(Splitter.on(",").splitToList(dnsSearch)));
		}

		if (isNotBlank(payload)) {
			createContainerCmd.withCmd(command, "-payload", "/tmp/" + name + ".json");
		}

		String containerId = createContainerCmd.exec().getId();

		if (isNotBlank(payload)) {
			try {
				Files.write(Paths.get(name + ".json"), payload.getBytes());
			} catch (IOException e) {
				throw new JobExecutionException(e);
			}
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			TarOutputStream tos = new TarOutputStream(baos);

			try {
				File f = new File(name + ".json");
				tos.putNextEntry(new TarEntry(f, f.getName()));
				tos.write(payload.getBytes(), 0, payload.getBytes().length);
				tos.flush();
				tos.close();
				f.delete();
			} catch (IOException e) {
				throw new JobExecutionException(e);
			}

			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			dockerClient.copyArchiveToContainerCmd(containerId).withRemotePath("/tmp").withTarInputStream(bais).exec();
		}

		dockerClient.startContainerCmd(containerId).exec();

		WaitContainerResultCallback waitContainerResultCallback = new WaitContainerResultCallback();
		dockerClient.waitContainerCmd(containerId).exec(waitContainerResultCallback);
		int exitCode = waitContainerResultCallback.awaitStatusCode();
		dockerClient.logContainerCmd(containerId).withStdOut(true).withStdErr(true).withTimestamps(true).withTailAll()
				.exec(logHandler);
		try {
			logHandler.awaitCompletion();
		} catch (InterruptedException e) {
			// e.printStackTrace();
		}
		dockerClient.removeContainerCmd(containerId).withForce(true).exec();
		return exitCode;
	}
}
