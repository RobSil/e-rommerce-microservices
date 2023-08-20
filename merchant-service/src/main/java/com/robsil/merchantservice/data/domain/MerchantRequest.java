package com.robsil.merchantservice.data.domain;

import com.robsil.merchantservice.model.merchant.request.MerchantRequestStatus;
import com.robsil.userservice.data.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "merchant_requests")
public class MerchantRequest extends BaseEntity {

    @Column(name = "user_id")
    private Long userId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column
    private String email;
    @Column(length = 64, unique = true)
    private String token;
    @Column
    @Enumerated(value = EnumType.STRING)
    private MerchantRequestStatus status;
    @Column(name = "expired_at")
    private LocalDateTime expiredAt;
    @Column(name = "confirmed_at")
    private LocalDateTime confirmedAt;
    @Column(name = "decisioned_at")
    private LocalDateTime decisionedAt;

}
