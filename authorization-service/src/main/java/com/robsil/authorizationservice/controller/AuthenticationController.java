package com.robsil.authorizationservice.controller;

import com.robsil.authorizationservice.model.AuthenticationResponse;
import com.robsil.authorizationservice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Base64;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestParam String credentials) {
        String[] usernamePassword = new String(Base64.getDecoder().decode(credentials.getBytes())).split(":");
        if (usernamePassword.length != 2) {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(400), "Credentials invalid");
        }

        return new ResponseEntity<>(authenticationService.authenticate(usernamePassword[0], usernamePassword[1]), HttpStatus.OK);
    }

}
