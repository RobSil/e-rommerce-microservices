package com.robsil.erommerce.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@EnableKafka
@RequiredArgsConstructor
public class KafkaConfig {

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name("mailing")
                .partitions(3)
                .build();
    }

}
