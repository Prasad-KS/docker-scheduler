package org.paggarwal.dockerscheduler.config;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by paggarwal on 3/10/16.
 */
@Configuration
public class DockerConfig {

    @Bean
    public DockerClient dockerClient() {
        DockerClientConfig config = DockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost("unix:///var/run/docker.sock")
                .withDockerTlsVerify(false)
                .build();
        return DockerClientBuilder.getInstance(config).build();
    }
}
