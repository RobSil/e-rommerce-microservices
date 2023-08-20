package com.robsil.merchantservice.config;

import com.robsil.erommerce.jwtintegration.filter.JwtClientServiceVerifierFilter;
import com.robsil.erommerce.userentityservice.data.domain.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtClientServiceVerifierFilter jwtFilter;

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/merchantRequests/confirm")
                .permitAll();


        http
                .authorizeHttpRequests()
                .requestMatchers(
                        "/api/v1/merchantRequests/{id}/decide",
                        "/api/v1/merchantRequests/{id}"
                )
                .hasAnyAuthority(Authority.ADMIN.toString(), Authority.SUPER_ADMIN.toString());

        http
                .authorizeHttpRequests()
                .anyRequest()
                .authenticated();

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
