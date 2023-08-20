package com.robsil.merchantservice.model.merchant.request;

import jakarta.validation.constraints.NotNull;

public record MerchantRequestDecideDto(
        @NotNull
        MerchantRequestStatus status
) {
}
