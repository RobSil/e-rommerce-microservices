package com.robsil.merchantservice.service;

import com.robsil.merchantservice.data.domain.MerchantRequest;
import com.robsil.merchantservice.data.repository.MerchantRequestRepository;
import com.robsil.merchantservice.model.merchant.request.MerchantRequestCreateDto;
import com.robsil.merchantservice.model.merchant.request.MerchantRequestStatus;
import com.robsil.model.exception.http.EntityNotFoundException;
import com.robsil.model.exception.http.HttpConflictException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MerchantRequestService {

    private final MerchantRequestRepository merchantRequestRepository;

    private static final int MAX_RETRY_COUNT = 10;
    private static final int PENDING_REQUEST_TTL = 86400;

    public MerchantRequest saveEntity(MerchantRequest entity) {
        return merchantRequestRepository.save(entity);
    }

    public MerchantRequest create(MerchantRequestCreateDto dto, boolean retry) {
        if (!retry) {
            return create(dto);
        }

        for (int i = 0; i < MAX_RETRY_COUNT; i++) {
            try {
                return create(dto);
            } catch (DataIntegrityViolationException e) {
                log.info("create with retry: got DataIntegrityViolationException, message: " + e.getMessage());
                if (i == MAX_RETRY_COUNT - 1) {
                    throw new HttpConflictException("Couldn't not create request, token unique violation.", e);
                }
            }
        }

        throw new HttpConflictException("Couldn't not create request, token unique violation.");
    }

    public MerchantRequest create(MerchantRequestCreateDto dto) {
        var mr = MerchantRequest.builder()
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .email(dto.email())
                .phoneNumber(dto.phoneNumber())
                .token(dto.token())
                .status(MerchantRequestStatus.PENDING)
                .expiredAt(LocalDateTime.now().plusSeconds(PENDING_REQUEST_TTL))
                .userId(dto.userId())
                .build();

        return saveEntity(mr);
    }

    public MerchantRequest findByToken(String token) {
        return merchantRequestRepository.findByToken(token)
                .orElseThrow(() -> new EntityNotFoundException("Merchant request not found by token, token: " + token));
    }

    public MerchantRequest findById(Long id) {
        return merchantRequestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Merchant request not found by id, id: " + id));
    }

    public List<MerchantRequest> findAllByEmailAndStatusesIn(String email, List<MerchantRequestStatus> statuses) {
        return merchantRequestRepository.findAllByEmailAndStatusIn(email, statuses);
    }

    public List<MerchantRequest> findAnyCreationInappropriateByEmail(String email) {
        return findAllByEmailAndStatusesIn(email, List.of(MerchantRequestStatus.PENDING, MerchantRequestStatus.CONFIRMED, MerchantRequestStatus.ACCEPTED));
    }

    public MerchantRequest changeStatus(Long id, MerchantRequestStatus status) {
        var mr = findById(id);

        mr.setStatus(status);
        mr = saveEntity(mr);

        return mr;
    }

    @Transactional
    public void deleteAllPendingObsolete(LocalDateTime now) {
        merchantRequestRepository.deleteAllPendingObsolete(now);
    }

    public void deleteById(Long id) {
        merchantRequestRepository.deleteById(id);
    }
}
