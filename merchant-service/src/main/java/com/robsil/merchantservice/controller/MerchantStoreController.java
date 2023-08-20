package com.robsil.merchantservice.controller;

import com.robsil.erommerce.jwtintegration.model.UserAuthenticationToken;
import com.robsil.merchantservice.model.merchant.store.MerchantStoreCreateRequest;
import com.robsil.merchantservice.model.merchant.store.MerchantStoreDto;
import com.robsil.merchantservice.model.merchant.store.MerchantStoreSaveRequest;
import com.robsil.merchantservice.service.MerchantStoreService;
import com.robsil.merchantservice.service.mapper.MerchantStoreDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchantStores")
@RequiredArgsConstructor
public class MerchantStoreController {

    private final MerchantStoreService merchantStoreService;
    private final MerchantStoreDtoMapper merchantStoreMapper;

    @GetMapping
    public MerchantStoreDto getByUser(UserAuthenticationToken user) {
        return merchantStoreMapper.apply(merchantStoreService.findByUserId(user.getId()));
    }

    @PostMapping
    public MerchantStoreDto create(@RequestBody @Valid MerchantStoreCreateRequest req, UserAuthenticationToken user) {
        return merchantStoreMapper.apply(merchantStoreService.create(req, user));
    }

    @PutMapping
    public MerchantStoreDto save(@RequestBody @Valid MerchantStoreSaveRequest req, UserAuthenticationToken user) {
        return merchantStoreMapper.apply(merchantStoreService.save(req, user));
    }
}
