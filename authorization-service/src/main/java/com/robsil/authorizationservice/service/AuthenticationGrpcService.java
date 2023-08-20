package com.robsil.authorizationservice.service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.robsil.model.exception.grpc.GrpcNotFoundException;
import com.robsil.proto.*;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
@GrpcService
public class AuthenticationGrpcService extends AuthenticationServiceGrpc.AuthenticationServiceImplBase {

    private final AuthenticationService authenticationService;

    @Override
    public void verifyToken(Token request, io.grpc.stub.StreamObserver<com.robsil.proto.VerificationResponse> responseObserver) {
        try {
            var result = authenticationService.verifyToken(request.getValue(), request.getCheckIsMerchant());
            var response = com.robsil.proto.VerificationResponse.newBuilder()
                    .setId(result.getId())
                    .setUsername(result.getUsername())
                    .setIsMerchant(result.isMerchant())
                    .addAllAuthorities(result.getAuthorities());

            responseObserver.onNext(response.build());
            responseObserver.onCompleted();
        } catch (JWTVerificationException e) {
            responseObserver.onError(new StatusRuntimeException(Status.UNAUTHENTICATED));
        }
    }

    @Override
    public void authenticate(Credentials request, StreamObserver<AuthenticationResponse> responseObserver) {
        try {
            var response = authenticationService.authenticate(request.getUsername(), request.getPassword());

            responseObserver.onNext(AuthenticationResponse.newBuilder()
                    .setIsAuthenticated(response.isAuthenticated())
                    .setUsername(response.getUsername())
                    .setToken(response.getToken())
                    .addAllAuthorities(response.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                    .build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("authenticate grpc: ex occurred, message: " + e.getMessage());
            responseObserver.onError(new StatusRuntimeException(Status.UNAUTHENTICATED));
        }
    }
}
