package org.paggarwal.dockerscheduler.config;

import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.google.common.base.Splitter;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Created by paggarwal on 3/21/16.
 */
@Configuration
public class AuthConfig {

    @Value("#{ systemEnvironment['TOKEN_SECRET'] ?: 'secret' }")
    private String tokenSecret;

    @Bean
    public JWTSigner jwtSigner() {
        return new JWTSigner(tokenSecret);
    }

    @Bean
    public JWTVerifier jwtVerifier() {
        return new JWTVerifier(tokenSecret);
    }
}
