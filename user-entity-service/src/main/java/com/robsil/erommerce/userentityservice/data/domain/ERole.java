package com.robsil.erommerce.userentityservice.data.domain;

import lombok.Getter;

@Getter
public enum ERole {
    USER("USER"), ADMIN("ADMIN"), SUPERADMIN("SUPERADMIN");

    private final String value;

    ERole(String value) {
        this.value = value;
    }
}
