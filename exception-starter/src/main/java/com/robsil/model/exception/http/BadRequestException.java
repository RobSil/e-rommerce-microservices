package com.robsil.model.exception.http;

import org.springframework.http.HttpStatus;

public class BadRequestException extends HttpException {
    public BadRequestException() {
        super(HttpStatus.BAD_REQUEST.value());
    }

    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST.value());
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause, HttpStatus.BAD_REQUEST.value());
    }

    public BadRequestException(Throwable cause) {
        super(cause, HttpStatus.BAD_REQUEST.value());
    }
}
