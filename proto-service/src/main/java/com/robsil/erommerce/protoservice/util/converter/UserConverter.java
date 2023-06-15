package com.robsil.erommerce.protoservice.util.converter;

import com.robsil.erommerce.protoservice.util.model.User;

public class UserConverter {

    private UserConverter() {
    }

    public static User toJava(com.robsil.proto.User user) {
        return new User(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getGender().toString(),
                user.getEmail(),
                user.getEmailConfirmed(),
                user.getPassword(),
                user.getIsEnabled(),
                user.getRolesList().stream().map(Enum::toString).toList());
    }
}
