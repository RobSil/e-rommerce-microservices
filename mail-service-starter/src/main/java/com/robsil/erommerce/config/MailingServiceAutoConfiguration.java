package com.robsil.erommerce.config;

import com.robsil.erommerce.service.MailingService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class MailingServiceAutoConfiguration {

    @Bean
    @ConditionalOnClass(KafkaTemplate.class)
    public MailingService mailingService(KafkaTemplate<String, String> kafkaTemplate) {
        return new MailingService(kafkaTemplate);
    }

}
