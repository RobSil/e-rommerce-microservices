package com.robsil.userservice.service.impl;

import com.robsil.model.exception.grpc.GrpcNotFoundException;
import com.robsil.model.exception.http.EntityNotFoundException;
import com.robsil.proto.Id;
import com.robsil.proto.Str;
import com.robsil.proto.User;
import com.robsil.proto.UserServiceGrpc;
import com.robsil.userservice.service.UserService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.stereotype.Service;

@Service
@GrpcService
@RequiredArgsConstructor
public class UserGrpcService extends UserServiceGrpc.UserServiceImplBase {

    private final UserService userService;

    @Override
    public void findById(Id request, StreamObserver<User> responseObserver) {
        try {
            responseObserver.onNext(userService.findById(request.getId()).toProto());
            responseObserver.onCompleted();
        } catch (EntityNotFoundException e) {
            responseObserver.onError(new GrpcNotFoundException(e));
        }
    }

    @Override
    public void findByEmail(Str request, StreamObserver<com.robsil.proto.User> responseObserver) {
        try {
            responseObserver.onNext(userService.findByEmail(request.getText()).toProto());
            responseObserver.onCompleted();
        } catch (EntityNotFoundException e) {
            responseObserver.onError(new GrpcNotFoundException(e));
        }
    }

}
