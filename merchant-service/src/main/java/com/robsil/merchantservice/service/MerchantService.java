package com.robsil.merchantservice.service;

import com.robsil.merchantservice.data.domain.Merchant;
import com.robsil.merchantservice.data.repository.MerchantRepository;
import com.robsil.merchantservice.model.merchant.MerchantCreateDto;
import com.robsil.model.exception.http.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MerchantService {

    private final MerchantRepository merchantRepository;

    public Merchant saveEntity(Merchant merchant) {
        return merchantRepository.save(merchant);
    }

    public Merchant findByUserId(Long userId) {
        return merchantRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Merchant by user id not found. userId: " + userId));
    }

    public Merchant create(MerchantCreateDto dto) {
        var merchant = new Merchant(dto.userId(),
                dto.request(),
                dto.firstName(),
                dto.lastName(),
                dto.email(),
                dto.phoneNumber(),
                true);

        merchant = saveEntity(merchant);

        return merchant;
    }

}
