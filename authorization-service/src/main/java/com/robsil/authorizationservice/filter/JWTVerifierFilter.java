package com.robsil.authorizationservice.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.robsil.authorizationservice.util.RSAHolder;
import com.robsil.authorizationservice.util.StringUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import static com.robsil.authorizationservice.service.AuthenticationService.*;

@Component
@RequiredArgsConstructor
public class JWTVerifierFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final RSAHolder rsaHolder;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = httpServletRequest.getHeader(AUTHORIZATION_HEADER);
        if (StringUtil.isNullOrEmpty(bearerToken) || !bearerToken.startsWith(BEARER_PREFIX)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        String authToken = bearerToken.replace(BEARER_PREFIX, "");

        var verifier = JWT
                .require(Algorithm.RSA256(rsaHolder.getPublicKey(), rsaHolder.getPrivateKey()))
                .withClaimPresence(USERNAME_CLAIM_NAME)
                .withClaimPresence(ID_CLAIM_NAME)
                .withClaimPresence(AUTHORITIES_CLAIM_NAME)
                .build();

        var decodedJwt = verifier.verify(authToken);

        Map<String, Object> payload = objectMapper.readValue(Base64.getDecoder().decode(decodedJwt.getPayload()), new TypeReference<Map<String, Object>>() {
        });

        String username = (String) payload.get("username");

        List<String> authorities = (List<String>) payload.get(AUTHORITIES_CLAIM_NAME);
        List<SimpleGrantedAuthority> grantedAuthorities = authorities
                .stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, grantedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        httpServletRequest.setAttribute(USERNAME_CLAIM_NAME, username);
        httpServletRequest.setAttribute(AUTHORITIES_CLAIM_NAME, grantedAuthorities);
        httpServletRequest.setAttribute("jwt", authToken);

        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }
}
