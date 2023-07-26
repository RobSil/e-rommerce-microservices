package com.robsil.userservice.service;


import com.robsil.erommerce.userentityservice.data.domain.User;
import com.robsil.userservice.user.UserRegistrationRequest;

import java.security.Principal;
import java.util.List;

public interface UserService {

    List<User> findAll();
    User findById(Long userId);
    User findByPrincipal(Principal principal);
    User findByEmail(String email);
    User saveEntity(User user);
    User register(UserRegistrationRequest dto);
    boolean existsByEmail(String email);
    long countAll();
}
