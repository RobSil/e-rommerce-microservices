package com.robsil.erommerce.service;

import jakarta.mail.Message;

import java.util.List;

public interface MailService {
    void sendEmail(List<String> sendTo,
                   Message.RecipientType recipientType,
                   String subject,
                   String content,
                   String contentType);
}
