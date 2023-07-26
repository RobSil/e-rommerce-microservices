package com.robsil.erommerce.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.robsil.erommerce.model.MailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final MailService mailService;

    @KafkaListener(topics = "mailing", concurrency = "3")
    public void handleMailing(String input) {
        log.info("handleMailing: got input: {}", input);

        try {
            var dto = objectMapper.readValue(input, MailDto.class);
            log.info("handleMailing: read dto. Dto: {}", dto);
            mailService.sendEmail(dto.sendTo(), dto.recipientType().getJakartaType(), dto.subject(), dto.content(), dto.contentType());
        } catch (Exception e) {
            log.info("handleMailing: got exception during handling mail. Message: {}", e.getMessage());
            e.printStackTrace();
            //todo dead letter queue
        }
    }
}
