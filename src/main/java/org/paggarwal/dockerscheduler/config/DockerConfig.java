package org.paggarwal.dockerscheduler.config;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

    @Value("#{ systemEnvironment['DOCKER_HOST'] ?: 'unix:///var/run/docker.sock' }")
    private String dockerHost;

    @Bean
    public DockerClient dockerClient() {
        DockerClientConfig.DockerClientConfigBuilder configBuilder = DockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost(dockerHost).withDockerTlsVerify(false);
        if(StringUtils.isNotBlank(registryUrl)) {
            configBuilder.withRegistryUrl(registryUrl)
                    .withRegistryUsername(registryUsername)
                    .withRegistryPassword(registryPassword)
                    .withRegistryEmail(registryEmail);

        }

        return DockerClientBuilder.getInstance(configBuilder.build()).build();
    }
}
