package com.robsil.userservice;

import com.robsil.erommerce.userentityservice.data.domain.*;
import com.robsil.userservice.data.repository.RoleRepository;
import com.robsil.userservice.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
@RequiredArgsConstructor
@Log4j2
@EntityScan(basePackages = {"com.robsil.erommerce.userentityservice.data.domain"})
public class UserServiceApplication implements ApplicationRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (userRepository.count() > 0L) {
            return;
        }

        var roleOptional = roleRepository.findByCode(ERole.SUPER_ADMIN.toString());
        if (roleOptional.isEmpty()) {
            roleOptional = Optional.of(roleRepository.save(Role.builder()
                    .name(ERole.SUPER_ADMIN.toString())
                    .code(ERole.SUPER_ADMIN.toString())
                    .authorities(List.of(Authority.SUPER_ADMIN))
                    .build()));
        }

        var role = roleOptional.get();

        log.info("creating default admin User.");
        userRepository.save(User.builder()
                .firstName("Rob")
                .lastName("Sil")
                .dateOfBirth(LocalDateTime.of(1991, 12, 18, 0, 0, 0))
                .email("robsil@erommerce.com")
                .emailConfirmed(true)
                .gender(Gender.MALE)
                .password(passwordEncoder.encode("1414"))
                .isEnabled(true)
                .roles(List.of(role))
                .build());
    }
}
