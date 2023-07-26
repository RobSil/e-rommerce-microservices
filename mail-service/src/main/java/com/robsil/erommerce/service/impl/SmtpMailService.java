package com.robsil.erommerce.service.impl;

import com.robsil.erommerce.service.MailService;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Properties;

@Service
@Slf4j
public class SmtpMailService implements MailService {

    private final Session session;
    private final String emailFrom;

    public SmtpMailService(@Value("${mail.smtp.host}") String smtpHost,
                           @Value("${mail.smtp.port}") int smtpPort,
                           @Value("${mail.smtp.username}") String smtpUsername,
                           @Value("${mail.smtp.password}") String smtpPassword,
                           @Value("${mail.smtp.emailFrom}") String emailFrom,
                           @Value("${mail.smtp.starttls}") boolean smtpStartTls) {

        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", smtpStartTls);
        prop.put("mail.smtp.host", smtpHost);
        prop.put("mail.smtp.port", smtpPort);

        session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(smtpUsername, smtpPassword);
            }
        });
        this.emailFrom = emailFrom;
    }

    @Override
    public void sendEmail(List<String> sendTo,
                          Message.RecipientType recipientType,
                          String subject,
                          String content,
                          String contentType) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailFrom));
            message.setRecipients(
                    recipientType, InternetAddress.parse(String.join(",", sendTo)));

            message.setSubject(subject);

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(content, contentType);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            Transport.send(message);
        } catch (Exception e) {
            log.error("sendEmail: exception occurred during sending email.");
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
