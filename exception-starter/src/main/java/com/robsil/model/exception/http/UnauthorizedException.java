package com.robsil.model.exception.http;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends HttpException {

    public UnauthorizedException() {
        super(HttpStatus.UNAUTHORIZED.value());
    }

    public UnauthorizedException(String message) {
        super(message, HttpStatus.UNAUTHORIZED.value());
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause, HttpStatus.UNAUTHORIZED.value());
    }

    public UnauthorizedException(Throwable cause) {
        super(cause, HttpStatus.UNAUTHORIZED.value());
    }

}
