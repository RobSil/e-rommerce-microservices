package com.robsil.model.exception.http;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public abstract class HttpException extends RuntimeException {

    private final Long timestamp = System.currentTimeMillis();

    private final int status;

    protected HttpException(int status) {
        this.status = status;
    }

    protected HttpException(String message, int status) {
        super(message);
        this.status = status;
    }

    protected HttpException(String message, Throwable cause, int status) {
        super(message, cause);
        this.status = status;
    }

    protected HttpException(Throwable cause, int status) {
        super(cause);
        this.status = status;
    }
}
