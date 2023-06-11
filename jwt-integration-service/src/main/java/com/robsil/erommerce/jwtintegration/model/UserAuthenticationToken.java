package com.robsil.erommerce.jwtintegration.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@EqualsAndHashCode
public class UserAuthenticationToken extends UsernamePasswordAuthenticationToken {

    @Getter
    private final Long id;

    public UserAuthenticationToken(Object principal, Object credentials, Long id) {
        super(principal, credentials);
        this.id = id;
    }

    public UserAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities, Long id) {
        super(principal, credentials, authorities);
        this.id = id;
    }
}
