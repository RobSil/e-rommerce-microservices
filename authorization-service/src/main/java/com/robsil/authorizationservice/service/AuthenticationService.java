package com.robsil.authorizationservice.service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.discovery.EurekaClient;
import com.robsil.authorizationservice.model.AuthenticationResponse;
import com.robsil.authorizationservice.model.VerificationResponse;
import com.robsil.authorizationservice.util.RSAHolder;
import com.robsil.erommerce.protoservice.util.util.IdUtil;
import com.robsil.erommerce.userentityservice.data.domain.User;
import com.robsil.proto.MerchantServiceGrpc;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
@RequiredArgsConstructor
public class AuthenticationService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final AuthenticationManager authenticationManager;
    private final RSAHolder rsaHolder;
    private final ApplicationContext applicationContext;
    private final EurekaClient eurekaClient;

    @GrpcClient("merchant-service")
    private MerchantServiceGrpc.MerchantServiceBlockingStub merchantServiceBlockingStub;

    public static final String USERNAME_CLAIM_NAME = "username";
    public static final String ID_CLAIM_NAME = "id";
    public static final String AUTHORITIES_CLAIM_NAME = "authorities";

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
                .withClaim(USERNAME_CLAIM_NAME, user.getEmail())
                .withClaim(ID_CLAIM_NAME, user.getId())
                .withClaim(
                        AUTHORITIES_CLAIM_NAME,
                        user.getAuthorities()
                                .stream()
                                .map(GrantedAuthority::getAuthority)
                                .toList()
                )
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
    public VerificationResponse verifyToken(String token, boolean checkIsMerchant) {

        var verifier = JWT
                .require(Algorithm.RSA256(rsaHolder.getPublicKey(), rsaHolder.getPrivateKey()))
                .withClaimPresence(USERNAME_CLAIM_NAME)
                .withClaimPresence(ID_CLAIM_NAME)
                .withClaimPresence(AUTHORITIES_CLAIM_NAME)
                .build();

        var decodedJwt = verifier.verify(token);

        Map<String, Object> payload = objectMapper.readValue(Base64.getDecoder().decode(decodedJwt.getPayload()), new TypeReference<Map<String, Object>>() {
        });

        var id = (long) ((Integer) payload.get("id"));
        var response = VerificationResponse
                .builder()
                .id(id)
                .username((String) payload.get(USERNAME_CLAIM_NAME))
                .authorities((List<String>) payload.get(AUTHORITIES_CLAIM_NAME));

        if (checkIsMerchant) {
            var merchant = merchantServiceBlockingStub.findByUserId(IdUtil.of(id));
            if (merchant != null) {
                response.isMerchant(true);
            }
        }

        return response
                .build();
    }
}
