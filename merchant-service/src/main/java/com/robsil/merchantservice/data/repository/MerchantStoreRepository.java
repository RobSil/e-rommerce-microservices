package com.robsil.merchantservice.data.repository;

import com.robsil.merchantservice.data.domain.MerchantStore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MerchantStoreRepository extends JpaRepository<MerchantStore, Long> {
    Optional<MerchantStore> findByMerchantId(Long id);

    Optional<MerchantStore> findByNameAndIdNot(String name, Long id);
}
