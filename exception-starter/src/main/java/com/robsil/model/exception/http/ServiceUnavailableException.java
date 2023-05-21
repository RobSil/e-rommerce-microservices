package com.robsil.model.exception.http;

import org.springframework.http.HttpStatus;

public class ServiceUnavailableException extends HttpException {

    public ServiceUnavailableException() {
        super(HttpStatus.SERVICE_UNAVAILABLE.value());
    }

    public ServiceUnavailableException(String message) {
        super(message, HttpStatus.SERVICE_UNAVAILABLE.value());
    }

    public ServiceUnavailableException(String message, Throwable cause) {
        super(message, cause, HttpStatus.SERVICE_UNAVAILABLE.value());
    }

    public ServiceUnavailableException(Throwable cause) {
        super(cause, HttpStatus.SERVICE_UNAVAILABLE.value());
    }

}
