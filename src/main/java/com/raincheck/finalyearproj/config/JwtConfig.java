package com.raincheck.finalyearproj.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
@Configuration
@ConfigurationProperties(prefix = "jwt") // 对应 yml 中的 jwt 前缀
public class JwtConfig {
    private String header;
    private String secret;
    private Long expiration;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}