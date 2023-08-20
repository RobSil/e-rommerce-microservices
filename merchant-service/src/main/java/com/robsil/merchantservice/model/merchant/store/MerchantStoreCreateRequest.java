package com.robsil.merchantservice.model.merchant.store;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record MerchantStoreCreateRequest(
        @NotEmpty
        String name,
        @NotEmpty
        List<MerchantStoreContact> contacts
) {
}
