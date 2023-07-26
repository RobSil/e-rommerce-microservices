package com.robsil.erommerce.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.robsil.erommerce.model.MailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

@RequiredArgsConstructor
@Slf4j
public class MailingService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void sendMail(MailDto mail) {
        try {
            kafkaTemplate.send("mailing", objectMapper.writeValueAsString(mail));
        } catch (Exception e) {
            log.info("sendMail: exception, message: {}", e.getMessage());
            e.printStackTrace();
        }
    }

}
