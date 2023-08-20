package com.robsil.merchantservice.data.repository;

import com.robsil.merchantservice.data.domain.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MerchantRepository extends JpaRepository<Merchant, Long> {
    Optional<Merchant> findByUserId(Long userId);
    Optional<Merchant> findByEmail(String email);
}
