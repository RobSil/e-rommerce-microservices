package com.robsil.userservice.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class OAuth2User implements org.springframework.security.oauth2.core.user.OAuth2User {

    private final Set<GrantedAuthority> authorities;
    private final Map<String, Object> attributes;
    private final String username;

    public OAuth2User(Set<GrantedAuthority> authorities, Map<String, Object> attributes, String username) {
        this.authorities = authorities;
        this.attributes = attributes;
        this.username = username;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return username;
    }

    public Long getId() {
        return (Long) this.attributes.get("id");
    }
}
