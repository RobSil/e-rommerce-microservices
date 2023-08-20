package com.robsil.erommerce.jwtintegration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class CsrfSecurityChainConfig {
    @Bean
    public SecurityFilterChain csrfSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();

        return http.build();
    }
}
