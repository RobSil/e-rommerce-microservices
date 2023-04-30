package com.robsil.model.exception.http;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends HttpException {

    public ForbiddenException() {
        super(HttpStatus.FORBIDDEN.value());
    }

    public ForbiddenException(String message) {
        super(message, HttpStatus.FORBIDDEN.value());
    }

    public ForbiddenException(String message, Throwable cause) {
        super(message, cause, HttpStatus.FORBIDDEN.value());
    }

    public ForbiddenException(Throwable cause) {
        super(cause, HttpStatus.FORBIDDEN.value());
    }

}
