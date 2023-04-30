package com.robsil.userservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
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

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserService userService;
    private final ObjectMapper objectMapper;
    private final UserDtoMapper userDtoMapper;

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

//        http.authorizeHttpRequests()
//                .requestMatchers("/**")
//                .permitAll();

        http.userDetailsService(userDetailsService);
        var provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);

//        http
//                .exceptionHandling()
//                    .authenticationEntryPoint((request, response, authException) -> {
//                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
//                    response.getWriter().write(unauthorizedMessage);
//                    response.getWriter().flush();
//                });

        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .maximumSessions(5);

//        http
//                .formLogin(Customizer.withDefaults());
//                .loginProcessingUrl("/api/login")
//                .successHandler(((request, response, authentication) -> {
//                    response.setStatus(HttpStatus.OK.value());
//
//                    try {
//                        var user = userService.findByEmail(AuthenticationUtil.getNameFromAuthentication(authentication));
//
//                        response.getWriter().write(objectMapper.writeValueAsString(userDtoMapper.apply(user)));
//                        response.getWriter().flush();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        throw new UnauthorizedException("Occurred unexpectable thing during fetching user.", e);
//                    }
//                }))
//                .failureHandler(((request, response, exception) -> {
//                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
//                    response.getWriter().write("Unauthorized");
//                    response.getWriter().flush();
//                }))
//        ;

//        http
//                .logout()
//                .logoutUrl("/api/logout")
//                .invalidateHttpSession(true)
//                .deleteCookies("SESSION", "JSESSIONID")
//                .logoutSuccessHandler(((request, response, authentication) -> response.setStatus(HttpStatus.OK.value())));

        http.oauth2ResourceServer()
                        .jwt();

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

        return http.build();
    }


}
