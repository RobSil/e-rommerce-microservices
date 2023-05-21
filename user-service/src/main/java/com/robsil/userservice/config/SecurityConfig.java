package com.robsil.userservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.robsil.erommerce.jwtintegration.filter.JwtClientServiceVerifierFilter;
import com.robsil.model.exception.http.EntityNotFoundException;
import com.robsil.userservice.service.UserDtoMapper;
import com.robsil.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserService userService;
    private final ObjectMapper objectMapper;
    private final UserDtoMapper userDtoMapper;
    private final JwtClientServiceVerifierFilter jwtFilter;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            try {
                return userService.findByEmail(username);
            } catch (EntityNotFoundException e) {
                throw new UsernameNotFoundException(e.getMessage());
            }
        };
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http, UserDetailsService userDetailsService) throws Exception {
        http.csrf().disable();

        http.userDetailsService(userDetailsService);
        var provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);

        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .maximumSessions(5);

        http
                .authorizeHttpRequests()
                .requestMatchers(
                        "/api/login",
                        "/login",
                        "/api/v1/users/register",
                        "/api/logout"
                )
                .permitAll();

        http
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/users/**")
                .authenticated();

        http
                .authorizeHttpRequests()
                .anyRequest()
                .permitAll();

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}
