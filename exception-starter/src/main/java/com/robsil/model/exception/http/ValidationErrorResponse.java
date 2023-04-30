package com.robsil.model.exception.http;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorResponse extends HttpException {

    @Getter
    private List<Violation> violations = new ArrayList<>();

    public ValidationErrorResponse() {
        super(HttpStatus.BAD_REQUEST.value());
    }

    public ValidationErrorResponse(String message) {
        super(message, HttpStatus.BAD_REQUEST.value());
    }

    public ValidationErrorResponse(String message, Throwable cause) {
        super(message, cause, HttpStatus.BAD_REQUEST.value());
    }

    public ValidationErrorResponse(Throwable cause) {
        super(cause, HttpStatus.BAD_REQUEST.value());
    }

}
