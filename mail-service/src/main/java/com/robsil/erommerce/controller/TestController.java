package com.robsil.erommerce.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TestController {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @SneakyThrows
    @GetMapping("/api/kafka/test")
    public void testKafka(@RequestParam String topic, @RequestParam String payload) {
        SendResult<String, String> result = kafkaTemplate.send(topic, payload).get();
        log.info(result.getProducerRecord().toString());
    }

}
