package com.robsil.model.exception.http;

import org.springframework.http.HttpStatus;

public class NotImplementedException extends HttpException {

    public NotImplementedException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    public NotImplementedException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    public NotImplementedException(String message, Throwable cause) {
        super(message, cause, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    public NotImplementedException(Throwable cause) {
        super(cause, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

}
