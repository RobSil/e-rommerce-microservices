package com.robsil.erommerce.model;

import lombok.Builder;

import java.util.List;

@Builder
public record MailDto(
        List<String> sendTo,
        RecipientType recipientType,
        String subject,
        String content,
        String contentType
) {
    private static final String APPLICATION_JSON = "application/json";
    private static final String TEXT_PLAIN = "text/plain";

    public static MailDto json(String sendTo, String subject, String content) {
        return MailDto.builder()
                .sendTo(List.of(sendTo))
                .recipientType(RecipientType.TO)
                .subject(subject)
                .content(content)
                .contentType(APPLICATION_JSON)
                .build();
    }
    public static MailDto text(String sendTo, String subject, String content) {
        return MailDto.builder()
                .sendTo(List.of(sendTo))
                .recipientType(RecipientType.TO)
                .subject(subject)
                .content(content)
                .contentType(TEXT_PLAIN)
                .build();
    }

}
