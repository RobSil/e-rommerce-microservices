package com.robsil.merchantservice.controller;

import com.robsil.merchantservice.service.MerchantFacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/merchants")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantFacadeService merchantFacadeService;


}
