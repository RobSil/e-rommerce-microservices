package com.robsil.erommerce.jwtintegration.model;

import com.robsil.erommerce.userentityservice.data.domain.Authority;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@EqualsAndHashCode
public class UserAuthenticationToken extends UsernamePasswordAuthenticationToken {

    @Getter
    private final Long id;
    @Getter
    private final boolean isMerchant;

    public boolean isSuperAdmin() {
        return this.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("SUPER_ADMIN"));
    }

    public boolean is(Authority toFind) {
        return this.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals(toFind.toString()));
    }

    public boolean isIn(List<String> authorities) {
        return this.getAuthorities().stream().anyMatch(authority -> authorities.contains(authority.getAuthority()));
    }

    public UserAuthenticationToken(Object principal, Object credentials, Long id, boolean isMerchant) {
        super(principal, credentials);
        this.id = id;
        this.isMerchant = isMerchant;
    }

    public UserAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities, Long id, boolean isMerchant) {
        super(principal, credentials, authorities);
        this.id = id;
        this.isMerchant = isMerchant;
    }
}
