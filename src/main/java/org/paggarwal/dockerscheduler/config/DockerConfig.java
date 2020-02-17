package org.paggarwal.dockerscheduler.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DefaultDockerClientConfig.Builder;
import com.github.dockerjava.core.DockerClientBuilder;

/**
 * Created by paggarwal on 3/10/16.
 */
@Configuration
public class DockerConfig {



	@Value("#{ systemEnvironment['DOCKER_URL'] ?: 'unix:///var/run/docker.sock' }")
	private String dockerHost;

	@Bean
	public DockerClient dockerClient() {
		Builder configBuilder = DefaultDockerClientConfig.createDefaultConfigBuilder().withDockerHost(dockerHost)
				.withDockerTlsVerify(false);

		return DockerClientBuilder.getInstance(configBuilder.build()).build();
	}

	@Bean
	public AuthConfigFactory registryAuthConfig() {
		AuthConfigFactory authBean = new AuthConfigFactory();
		return authBean;
	}
}
