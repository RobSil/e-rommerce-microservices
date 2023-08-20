package com.robsil.authorizationservice.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class VerificationResponse {
    private Long id;
    private String username;
    private List<String> authorities;
    private boolean isMerchant;
}
