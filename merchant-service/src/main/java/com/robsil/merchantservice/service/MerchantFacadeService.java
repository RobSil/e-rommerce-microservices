package com.robsil.merchantservice.service;

import com.robsil.erommerce.service.MailingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MerchantFacadeService {

    private final MerchantService merchantService;
    private final MailingService mailingService;

}
