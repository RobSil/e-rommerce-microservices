package com.robsil.userservice;

import com.robsil.erommerce.userentityservice.data.domain.ERole;
import com.robsil.erommerce.userentityservice.data.domain.Gender;
import com.robsil.erommerce.userentityservice.data.domain.User;
import com.robsil.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
@Log4j2
@EntityScan(basePackages = { "com.robsil.erommerce.userentityservice.data.domain" })
public class UserServiceApplication implements ApplicationRunner {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (userService.countAll() == 0L) {
            log.info("creating default admin User.");
            userService.saveEntity(User.builder()
                    .firstName("Rob")
                    .lastName("Sil")
                    .dateOfBirth(LocalDateTime.of(1991, 12, 18, 0, 0, 0))
                    .email("robsil@erommerce.com")
                    .emailConfirmed(true)
                    .gender(Gender.MALE)
                    .password(passwordEncoder.encode("1414"))
                    .isEnabled(true)
                    .roles(List.of(ERole.SUPERADMIN, ERole.ADMIN))
                    .build());
        }
    }
}
