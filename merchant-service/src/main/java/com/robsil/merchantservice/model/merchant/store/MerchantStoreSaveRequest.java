package com.robsil.merchantservice.model.merchant.store;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record MerchantStoreSaveRequest(
        @NotNull
        @Positive
        Long id,
        @NotEmpty
        String name,
        @NotEmpty
        List<MerchantStoreContact> contacts
) {
}
