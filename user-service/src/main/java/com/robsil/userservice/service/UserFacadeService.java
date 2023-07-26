package com.robsil.userservice.service;

import com.robsil.erommerce.model.MailDto;
import com.robsil.erommerce.model.RecipientType;
import com.robsil.erommerce.service.MailingService;
import com.robsil.userservice.user.UserRegistrationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserFacadeService {

    private final UserService userService;
    private final MailingService mailingService;

    public void register(UserRegistrationRequest request) {
        var user = userService.register(request);

        mailingService.sendMail(new MailDto(List.of(user.getEmail()),
                RecipientType.TO,
                "Registration successful!",
                String.format("Dear, %s! You have successfully registered!", user.getFirstName()),
                ContentType.TEXT_HTML.getMimeType()));
    }

}
