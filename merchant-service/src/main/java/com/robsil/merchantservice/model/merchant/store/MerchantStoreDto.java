package com.robsil.merchantservice.model.merchant.store;


import java.util.List;

public record MerchantStoreDto(
        Long id,
        String name,
        boolean isNotBlocked,
        List<MerchantStoreContact> contacts
) {
}
