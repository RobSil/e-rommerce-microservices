//package com.robsil.authorizationservice.filter;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.robsil.authorizationservice.model.AuthenticationResponse;
//import com.robsil.authorizationservice.util.RSAHolder;
//import com.robsil.erommerce.userentityservice.data.domain.ERole;
//import com.robsil.erommerce.userentityservice.data.domain.User;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.security.interfaces.RSAPrivateKey;
//import java.security.interfaces.RSAPublicKey;
//import java.time.Instant;
//import java.time.temporal.ChronoUnit;
//
//@Component
//@RequiredArgsConstructor
//@Log4j2
//public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//
////    @Value("${jwt.hmac-salt}")
////    private String HMAC_SALT;
//    @Value("${eureka.instance.instanceId}")
//    private String EUREKA_INSTANCE_ID;
//
//    private final ObjectMapper objectMapper = new ObjectMapper();
//    private final AuthenticationManager authenticationManager;
//    private final RSAHolder rsaHolder;
//
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        var username = obtainUsername(request);
//        var password = obtainPassword(request);
//        UsernamePasswordAuthenticationToken authenticationToken = UsernamePasswordAuthenticationToken.unauthenticated(username,
//                password);
//
//
//        return authenticationManager.authenticate(authenticationToken);
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
//        var user = (User) authResult.getPrincipal();
//        String token = JWT.create()
//                .withIssuer(EUREKA_INSTANCE_ID)
//                .withIssuedAt(Instant.now())
//                .withExpiresAt(Instant.now().plus(7L, ChronoUnit.DAYS))
//                .withClaim("username", user.getEmail())
//                .withClaim("id", user.getId())
//                .withClaim("authorities", user.getRoles().stream().map(ERole::getValue).toList())
//                .sign(Algorithm.RSA256(rsaHolder.getPublicKey(), rsaHolder.getPrivateKey()));
//
//        log.info(token);
//
//        var respModel = AuthenticationResponse
//                .builder()
//                .status(HttpStatus.OK.name())
//                .token(String.format("Bearer %s", token))
//                .methodType(HttpMethod.GET.name())
//                .isAuthenticated(true)
//                .build();
//
//        response.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
//        response.getOutputStream().write(objectMapper.writeValueAsBytes(respModel));
//    }
//
////    @Override
////    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
////        super.unsuccessfulAuthentication(request, response, failed);
////    }
//}
