package com.robsil.merchantservice.model.merchant.request;


import lombok.Builder;

@Builder
public record MerchantRequestDto(
        String firstName,
        String lastName,
        String phoneNumber,
        String email,
        String token
) {
}
