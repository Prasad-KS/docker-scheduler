package org.paggarwal.dockerscheduler.config;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.RemoteApiVersion;
import com.spotify.docker.client.DefaultDockerClient;
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
                .withDockerHost("tcp://peeyush-Studio-1558:2375")
                .withRegistryUrl("https://registry.thrively.com")
                .withRegistryUsername("serioussam")
                .withRegistryPassword("xmc4VHCF")
                .withRegistryEmail("peeyush_16@yahoo.com")
                .withDockerTlsVerify(false)
                .withApiVersion(RemoteApiVersion.VERSION_1_22)
                .build();
        return DockerClientBuilder.getInstance(config).build();
    }

    @Bean
    public com.spotify.docker.client.DockerClient spotifyDockerClient() {
        return DefaultDockerClient.builder().uri("http://peeyush-Studio-1558:2375").build();
    }
}
