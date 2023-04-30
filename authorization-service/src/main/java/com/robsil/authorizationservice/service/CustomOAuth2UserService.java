package com.robsil.authorizationservice.service;

import com.robsil.authorizationservice.repository.UserRepository;
import com.robsil.erommerce.userentityservice.data.domain.User;
import com.robsil.model.exception.http.EntityNotFoundException;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import com.robsil.userservice.model.OAuth2User;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, org.springframework.security.oauth2.core.user.OAuth2User> {

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        User user = userRepository.findByEmail((String) userRequest.getAdditionalParameters().get("email")).orElseThrow(EntityNotFoundException::new);

        return new OAuth2User(Set.copyOf(user.getAuthorities()), Map.of("id", user.getId()), user.getUsername());
    }
}
