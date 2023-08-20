package com.robsil.merchantservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.Clock;

@Configuration
@EnableScheduling
public class SchedulingConfig {

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

}
