package com.robsil.userservice.util;

import com.robsil.model.exception.http.UnauthorizedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;

public class AuthenticationUtil {

    private AuthenticationUtil() {}

    public static String getNameFromAuthentication(Authentication authentication) {
        Object object = authentication.getPrincipal();

        if (object instanceof UserDetails principal) {
            return principal.getUsername();
        }

        if (object instanceof Principal principal) {
            return principal.getName();
        }

        throw new UnauthorizedException("Failed to get name from authentication.");
    }

}
