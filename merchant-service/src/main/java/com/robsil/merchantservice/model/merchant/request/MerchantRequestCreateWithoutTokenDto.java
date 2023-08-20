package com.robsil.merchantservice.model.merchant.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record MerchantRequestCreateWithoutTokenDto(
        @NotEmpty
        @Size(max = 32)
        String firstName,
        @NotEmpty
        @Size(max = 32)
        String lastName,
        @NotEmpty
        @Size(max = 64)
        @Email
        String email,
        @NotEmpty
        @NotEmpty
        @Size(max = 16)
        String phoneNumber
) {
}

