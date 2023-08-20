package com.robsil.merchantservice.model.merchant.request;

import jakarta.validation.constraints.NotEmpty;

public record MerchantRequestCreateDto(
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        @NotEmpty
        String token,
        Long userId
) {
}
