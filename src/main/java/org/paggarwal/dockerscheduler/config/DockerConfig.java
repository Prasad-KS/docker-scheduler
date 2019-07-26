package org.paggarwal.dockerscheduler.config;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.AuthConfig;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DefaultDockerClientConfig.Builder;
import com.github.dockerjava.core.DockerClientBuilder;

/**
 * Created by paggarwal on 3/10/16.
 */
@Configuration
public class DockerConfig {

	@Value("#{ systemEnvironment['REGISTRY_URL'] }")
	private String registryUrl;

	@Value("#{ systemEnvironment['REGISTRY_USERNAME']}")
	private String registryUsername;

	@Value("#{ systemEnvironment['REGISTRY_PASSWORD'] }")
	private String registryPassword;

	@Value("#{ systemEnvironment['REGISTRY_EMAIL'] }")
	private String registryEmail;

	@Value("#{ systemEnvironment['DOCKER_URL'] ?: 'unix:///var/run/docker.sock' }")
	private String dockerHost;

	@Bean
	public DockerClient dockerClient() {
		Builder configBuilder = DefaultDockerClientConfig.createDefaultConfigBuilder().withDockerHost(dockerHost)
				.withDockerTlsVerify(false);
		if (StringUtils.isNotBlank(registryUrl)) {
			configBuilder.withRegistryUrl(registryUrl).withRegistryUsername(registryUsername)
					.withRegistryPassword(registryPassword).withRegistryEmail(registryEmail);
		}

		return DockerClientBuilder.getInstance(configBuilder.build()).build();
	}

	@Bean
	public AuthConfig registryAuthConfig() {
		return new AuthConfig().withUsername(registryUsername).withPassword(registryPassword)
				.withRegistryAddress(registryUrl).withEmail(registryEmail);
	}
}
