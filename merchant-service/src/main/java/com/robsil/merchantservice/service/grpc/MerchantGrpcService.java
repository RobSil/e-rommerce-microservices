package com.robsil.merchantservice.service.grpc;

import com.robsil.merchantservice.service.MerchantService;
import com.robsil.model.exception.grpc.GrpcNotFoundException;
import com.robsil.model.exception.http.EntityNotFoundException;
import com.robsil.proto.Id;
import com.robsil.proto.Merchant;
import com.robsil.proto.MerchantServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@GrpcService
public class MerchantGrpcService extends MerchantServiceGrpc.MerchantServiceImplBase {

    private final MerchantService merchantService;

    @Override
    public void findByUserId(Id request, StreamObserver<Merchant> responseObserver) {
        try {
            var merchant = merchantService.findByUserId(request.getId());
            responseObserver.onNext(Merchant.newBuilder()
                    .setId(merchant.getId())
                    .setUserId(merchant.getUserId())
                    .setFirstName(merchant.getFirstName())
                    .setLastName(merchant.getLastName())
                    .setPhoneNumber(merchant.getPhoneNumber())
                    .setEmail(merchant.getEmail())
                    .setIsNotBlocked(merchant.isNotBlocked())
                    .build());
            responseObserver.onCompleted();
        } catch (EntityNotFoundException e) {
            throw new GrpcNotFoundException(e);
        }
    }
}
