package com.robsil.merchantservice.data.repository;

import com.robsil.merchantservice.data.domain.MerchantRequest;
import com.robsil.merchantservice.model.merchant.request.MerchantRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MerchantRequestRepository extends JpaRepository<MerchantRequest, Long> {
    Optional<MerchantRequest> findByToken(String token);

    @Modifying
    @Query("delete from MerchantRequest mr where mr.status = 'PENDING' and mr.expiredAt < :now")
    void deleteAllPendingObsolete(LocalDateTime now);

    List<MerchantRequest> findAllByEmailAndStatusIn(String email, List<MerchantRequestStatus> statuses);
}
