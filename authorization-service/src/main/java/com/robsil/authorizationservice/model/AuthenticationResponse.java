package com.robsil.authorizationservice.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Builder
@ToString
public class AuthenticationResponse {
    private boolean isAuthenticated;
    private String username;
    private String token;
    private Collection<? extends GrantedAuthority> authorities;
}
