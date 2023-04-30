package com.robsil.model.exception.http;

import org.springframework.http.HttpStatus;

public class HttpConflictException extends HttpException {

    public HttpConflictException() {
        super(HttpStatus.CONFLICT.value());
    }

    public HttpConflictException(String message) {
        super(message, HttpStatus.CONFLICT.value());
    }

    public HttpConflictException(String message, Throwable cause) {
        super(message, cause, HttpStatus.CONFLICT.value());
    }

    public HttpConflictException(Throwable cause) {
        super(cause, HttpStatus.CONFLICT.value());
    }

}
