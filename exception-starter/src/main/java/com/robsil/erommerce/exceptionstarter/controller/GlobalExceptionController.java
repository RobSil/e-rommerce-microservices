package com.robsil.erommerce.exceptionstarter.controller;

import com.robsil.model.exception.http.EntityNotFoundException;
import com.robsil.model.exception.http.HttpConflictException;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(HttpConflictException.class)
    public ProblemDetail onHttpConflictException(HttpConflictException e) {
        return ProblemDetail.forStatusAndDetail(HttpConflictException.CODE, e.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ProblemDetail onEntityNotFoundException(EntityNotFoundException e) {
        return ProblemDetail.forStatusAndDetail(EntityNotFoundException.CODE, e.getMessage());
    }

}
