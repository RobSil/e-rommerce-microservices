package com.robsil.model.exception.grpc;


import com.robsil.model.exception.http.EntityNotFoundException;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;

public class GrpcNotFoundException extends StatusRuntimeException {
    public GrpcNotFoundException(EntityNotFoundException exception) {
        super(Status.NOT_FOUND.withCause(exception).withDescription(exception.getMessage()));
    }
}
