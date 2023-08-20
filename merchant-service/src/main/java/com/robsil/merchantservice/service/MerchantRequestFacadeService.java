package com.robsil.merchantservice.service;

import com.robsil.erommerce.jwtintegration.model.UserAuthenticationToken;
import com.robsil.erommerce.model.MailDto;
import com.robsil.erommerce.service.MailingService;
import com.robsil.merchantservice.data.domain.MerchantRequest;
import com.robsil.merchantservice.model.merchant.MerchantCreateDto;
import com.robsil.merchantservice.model.merchant.request.*;
import com.robsil.merchantservice.util.TokenGenerator;
import com.robsil.model.exception.http.BadRequestException;
import com.robsil.model.exception.http.HttpConflictException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
@SuppressWarnings({"UnusedReturnValue"})
public class MerchantRequestFacadeService {

    private final MerchantRequestService merchantRequestService;
    private final MailingService mailingService;
    private final MerchantService merchantService;
    private final Clock clock;

    @Value("${server.host}")
    private String host;

    @Transactional
    public MerchantRequest create(MerchantRequestCreateWithoutTokenDto dto, UserAuthenticationToken user) {
        if (!merchantRequestService.findAnyCreationInappropriateByEmail(dto.email()).isEmpty()) {
            throw new HttpConflictException("There is already some requests, due which you can create a new one.");
        }

        var createDto = new MerchantRequestCreateDto(dto.firstName(),
                dto.lastName(),
                dto.email(),
                dto.phoneNumber(),
                TokenGenerator.generate(),
                user.getId());

        var request = merchantRequestService.create(createDto, true);

        mailingService.sendMail(MailDto.text(dto.email(),
                "Confirm merchant request.",
                "Confirm your merchant request: %s/api/v1/merchantRequests/confirm?token=%s".formatted(host, request.getToken())));

        return request;
    }

    @Transactional
    public MerchantRequest confirm(String token) {
        var mr = merchantRequestService.findByToken(token);

        if (!mr.getStatus().equals(MerchantRequestStatus.PENDING)) {
            throw new HttpConflictException("Merchant request status is not pending. Status: " + mr.getStatus().toString());
        }

        mr.setStatus(MerchantRequestStatus.CONFIRMED);
        mr.setConfirmedAt(LocalDateTime.now(clock));
        mr = merchantRequestService.saveEntity(mr);

        return mr;
    }

    @Transactional
    public MerchantRequest decide(Long id, MerchantRequestDecideDto dto) {
        if (!dto.status().equals(MerchantRequestStatus.ACCEPTED) && !dto.status().equals(MerchantRequestStatus.DECLINED)) {
            throw new BadRequestException("Should process only ACCEPTED or DECLINED. Status: " + dto.status());
        }

        var mr = merchantRequestService.findById(id);

        mr.setStatus(dto.status());
        mr.setDecisionedAt(LocalDateTime.now(clock));

        mr = merchantRequestService.saveEntity(mr);

        if (mr.getStatus().equals(MerchantRequestStatus.ACCEPTED)) {
            handleAcceptedRequest(mr);
        } else {
            handleDeclinedRequest(mr);
        }

        return mr;
    }

    public void handleAcceptedRequest(MerchantRequest mr) {
        merchantService.create(new MerchantCreateDto(mr));
        mailingService.sendMail(MailDto.text(mr.getEmail(),
                "Decision of request.",
                "Congratulations, your request is accepted! Now you can login in as merchant, under your usual account!"));
    }

    public void handleDeclinedRequest(MerchantRequest mr) {
        mailingService.sendMail(MailDto.text(mr.getEmail(),
                "Decision of request.",
                "We so sorry, but we have to decline your request! :("));
    }

}
