package com.robsil.userservice.service.impl;


import com.robsil.erommerce.userentityservice.data.domain.ERole;
import com.robsil.erommerce.userentityservice.data.domain.User;
import com.robsil.userservice.data.repository.UserRepository;
import com.robsil.model.exception.http.EntityNotFoundException;
import com.robsil.model.exception.http.HttpConflictException;
import com.robsil.model.exception.http.UnauthorizedException;
import com.robsil.userservice.user.UserRegistrationRequest;
import com.robsil.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.info("getByEmail: user not found. UserID: %s".formatted(userId));
                    return new EntityNotFoundException("User not found");
                });
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByPrincipal(Principal principal) {
        if (principal == null) throw new UnauthorizedException("UNAUTHORIZED");
        return findByEmail(principal.getName());
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.info("getByEmail: user not found. Email: %s".formatted(email));
                    return new EntityNotFoundException("User not found");
                });
    }

    @Override
    @Transactional
    public User saveEntity(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User register(UserRegistrationRequest dto) {

        if (existsByEmail(dto.getEmail())) {
            throw new HttpConflictException("Email is already occupied.");
        }

        var user = User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .dateOfBirth(dto.getDateOfBirth())
                .gender(dto.getGender())
                .password(passwordEncoder.encode(dto.getPassword()))
                .isEnabled(true)
                .build();

        user.getRoles().add(ERole.USER);

        return saveEntity(user);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public long countAll() {
        return userRepository.count();
    }
}
