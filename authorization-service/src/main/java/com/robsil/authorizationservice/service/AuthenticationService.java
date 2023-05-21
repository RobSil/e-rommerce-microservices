package com.robsil.authorizationservice.service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.robsil.authorizationservice.model.AuthenticationResponse;
import com.robsil.authorizationservice.model.VerificationResponse;
import com.robsil.authorizationservice.util.RSAHolder;
import com.robsil.erommerce.userentityservice.data.domain.ERole;
import com.robsil.erommerce.userentityservice.data.domain.User;
import com.robsil.proto.AuthenticationServiceGrpc;
import com.robsil.proto.Token;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
@RequiredArgsConstructor
@GrpcService
public class AuthenticationService extends AuthenticationServiceGrpc.AuthenticationServiceImplBase {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final AuthenticationManager authenticationManager;
    private final RSAHolder rsaHolder;

    @Override
    public void verifyToken(Token request, io.grpc.stub.StreamObserver<com.robsil.proto.VerificationResponse> responseObserver) {
        try {
            var result = verifyToken(request.getValue());
            var response = com.robsil.proto.VerificationResponse.newBuilder()
                    .setId(result.getId())
                    .setUsername(result.getUsername())
                    .addAllAuthorities(result.getAuthorities());

            responseObserver.onNext(response.build());
            responseObserver.onCompleted();
        } catch (JWTVerificationException e) {
            responseObserver.onError(new StatusRuntimeException(Status.UNAUTHENTICATED));
        }
    }

    public AuthenticationResponse authenticate(String username, String password) {
        Authentication authentication;
        //todo: handle authenticate errors
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            throw e;
        }
        User user = (User) authentication.getPrincipal();

        String token = JWT.create()
                .withIssuer("SINGLE_AUTHORIZATION_SERVICE")
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plus(7L, ChronoUnit.DAYS))
                .withClaim("username", user.getEmail())
                .withClaim("id", user.getId())
                .withClaim("authorities", user.getRoles().stream().map(ERole::getValue).toList())
                .sign(Algorithm.RSA256(rsaHolder.getPublicKey(), rsaHolder.getPrivateKey()));

        return AuthenticationResponse
                .builder()
                .token(token)
                .isAuthenticated(true)
                .authorities(user.getAuthorities())
                .username(user.getEmail())
                .build();

    }

    @SneakyThrows
    public VerificationResponse verifyToken(String token) {
        var verifier = JWT
                .require(Algorithm.RSA256(rsaHolder.getPublicKey(), rsaHolder.getPrivateKey()))
                .withClaimPresence("username")
                .withClaimPresence("id")
                .withClaimPresence("authorities")
                .build();

        var decodedJwt = verifier.verify(token);

        Map<String, Object> payload = objectMapper.readValue(Base64.getDecoder().decode(decodedJwt.getPayload()), new TypeReference<Map<String, Object>>() {
        });

        return VerificationResponse
                .builder()
                .id((long) ((Integer) payload.get("id")))
                .username((String) payload.get("username"))
                .authorities((List<String>) payload.get("authorities"))
                .build();
    }
}
