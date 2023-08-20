package com.robsil.merchantservice.service.mapper;

import com.robsil.merchantservice.data.domain.MerchantStore;
import com.robsil.merchantservice.model.merchant.store.MerchantStoreDto;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class MerchantStoreDtoMapper implements Function<MerchantStore, MerchantStoreDto> {
    @Override
    public MerchantStoreDto apply(MerchantStore mr) {
        return new MerchantStoreDto(mr.getId(), mr.getName(), mr.isNotBlocked(), mr.getContacts());
    }
}
