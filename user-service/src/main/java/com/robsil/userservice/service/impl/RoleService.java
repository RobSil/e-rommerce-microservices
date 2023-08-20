package com.robsil.userservice.service.impl;

import com.robsil.erommerce.userentityservice.data.domain.Role;
import com.robsil.model.exception.http.EntityNotFoundException;
import com.robsil.userservice.data.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role saveEntity(Role role) {
        return roleRepository.save(role);
    }

    public Role findByCode(String code, boolean createIfNotExists) {
        if (!createIfNotExists) {
            return findByCode(code);
        }

        try {
            return findByCode(code);
        } catch (EntityNotFoundException e) {
            return saveEntity(Role.builder()
                    .name(code)
                    .code(code)
                    .authorities(Collections.emptyList())
                    .build());
        }
    }

    public Role findByCode(String code) {
        return roleRepository.findByCode(code)
                .orElseThrow(() -> new EntityNotFoundException("Role not found by code. Code: " + code));
    }

}
