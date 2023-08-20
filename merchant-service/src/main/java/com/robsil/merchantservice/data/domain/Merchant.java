package com.robsil.merchantservice.data.domain;

import com.robsil.userservice.data.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "merchants")
public class Merchant extends BaseEntity {

    @Column(name = "user_id")
    private Long userId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id")
    private MerchantRequest request;
    @Column(name = "first_name", length = 32)
    private String firstName;
    @Column(name = "last_name", length = 32)
    private String lastName;
    @Column(unique = true, length = 64)
    private String email;
    @Column(unique = true, length = 16)
    private String phoneNumber;
    @Column(name = "is_not_blocked")
    private boolean isNotBlocked;
}
