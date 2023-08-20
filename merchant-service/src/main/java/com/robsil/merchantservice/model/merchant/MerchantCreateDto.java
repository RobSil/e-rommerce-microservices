package com.robsil.merchantservice.model.merchant;

import com.robsil.merchantservice.data.domain.MerchantRequest;

public record MerchantCreateDto(
        MerchantRequest request,
        Long userId,
        String firstName,
        String lastName,
        String email,
        String phoneNumber
) {
    public MerchantCreateDto(MerchantRequest request) {
        this(request, request.getUserId(), request.getFirstName(), request.getLastName(), request.getEmail(), request.getPhoneNumber());
    }
}
