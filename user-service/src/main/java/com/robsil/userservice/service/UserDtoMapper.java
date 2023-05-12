package com.robsil.userservice.service;

import com.robsil.erommerce.userentityservice.data.domain.User;
import com.robsil.userservice.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class UserDtoMapper implements Function<User, UserDto> {
    @Override
    public UserDto apply(User user) {

        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .dateOfBirth(user.getDateOfBirth())
                .gender(user.getGender())
                .emailConfirmed(user.isEmailConfirmed())
                .roles(user.getRoles())
                .build();
    }
}
