package com.robsil.model.exception.http;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class HttpException extends RuntimeException {

    private Long timestamp = System.currentTimeMillis();

    private int status;

    public HttpException(int status) {
        this.status = status;
    }

    public HttpException(String message, int status) {
        super(message);
        this.status = status;
    }

    public HttpException(String message, Throwable cause, int status) {
        super(message, cause);
        this.status = status;
    }

    public HttpException(Throwable cause, int status) {
        super(cause);
        this.status = status;
    }
}
