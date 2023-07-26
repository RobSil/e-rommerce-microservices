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
) {}
