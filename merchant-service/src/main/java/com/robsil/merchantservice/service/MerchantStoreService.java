package com.robsil.merchantservice.service;

import com.robsil.erommerce.jwtintegration.model.UserAuthenticationToken;
import com.robsil.merchantservice.data.domain.MerchantStore;
import com.robsil.merchantservice.data.repository.MerchantStoreRepository;
import com.robsil.merchantservice.model.merchant.store.MerchantStoreCreateRequest;
import com.robsil.merchantservice.model.merchant.store.MerchantStoreSaveRequest;
import com.robsil.model.exception.http.BadRequestException;
import com.robsil.model.exception.http.EntityNotFoundException;
import com.robsil.model.exception.http.HttpConflictException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MerchantStoreService {

    private final MerchantStoreRepository merchantStoreRepository;
    private final MerchantService merchantService;

    public MerchantStore saveEntity(MerchantStore mr) {
        return merchantStoreRepository.save(mr);
    }

    public MerchantStore findById(Long id) {
        return merchantStoreRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("MerchantStore by id not found. ID: " + id));
    }

    public MerchantStore findByMerchantId(Long id) {
        return merchantStoreRepository.findByMerchantId(id)
                .orElseThrow(() -> new EntityNotFoundException("MerchantStore by merchantId not found. ID: " + id));
    }

    public MerchantStore findByUserId(Long id) {
        return findByMerchantId(merchantService.findByUserId(id).getId());
    }

    @Transactional
    public MerchantStore create(MerchantStoreCreateRequest req, UserAuthenticationToken user) {
        var merchant = merchantService.findByUserId(user.getId());
        try {
            findByMerchantId(merchant.getId());
            throw new BadRequestException("Merchant store by merchant is already exists.");
        } catch (EntityNotFoundException ignored) {
        }

        var store = MerchantStore.builder()
                .merchant(merchant)
                .name(req.name())
                .isNotBlocked(true)
                .contacts(req.contacts())
                .build();

        store = saveEntity(store);

        return store;

    }

    @Transactional
    public MerchantStore save(MerchantStoreSaveRequest req, UserAuthenticationToken user) {
        var merchant = merchantService.findByUserId(user.getId());
        var store = findById(req.id());

        if (!store.getMerchant().getId().equals(merchant.getId())) {
            throw new HttpConflictException("Can't access foreign merchant store.");
        }

        try {
            var another = findByNameAndIdNot(req.name(), store.getId());
            throw new HttpConflictException("There is already store with this name and different id. Another store id: " + another.getId());
        } catch (EntityNotFoundException ignored) {}

        store.setName(req.name());
        store.setContacts(req.contacts());

        store = saveEntity(store);

        return store;
    }

    public MerchantStore findByNameAndIdNot(String name, Long id) {
        return merchantStoreRepository.findByNameAndIdNot(name, id)
                .orElseThrow(() -> new EntityNotFoundException("Couldn't find merchantStore by name: '%s', and id not: '%d'".formatted(name, id)));
    }
}
