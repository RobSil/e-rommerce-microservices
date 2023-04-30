package com.robsil.model.exception.http;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends HttpException {

    public EntityNotFoundException() {
        super(HttpStatus.NOT_FOUND.value());
    }

    public EntityNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND.value());
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause, HttpStatus.NOT_FOUND.value());
    }

    public EntityNotFoundException(Throwable cause) {
        super(cause, HttpStatus.NOT_FOUND.value());
    }
}
