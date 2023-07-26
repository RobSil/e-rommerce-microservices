package com.robsil.userservice.controller;

import com.robsil.erommerce.userentityservice.data.domain.User;
import com.robsil.userservice.service.UserFacadeService;
import com.robsil.userservice.user.UserRegistrationRequest;
import com.robsil.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final UserFacadeService userFacadeService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid UserRegistrationRequest dto) {
        userFacadeService.register(dto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
