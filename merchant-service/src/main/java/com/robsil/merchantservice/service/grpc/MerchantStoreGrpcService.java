package com.robsil.merchantservice.service.grpc;

import com.robsil.merchantservice.service.MerchantService;
import com.robsil.merchantservice.service.MerchantStoreService;
import com.robsil.model.exception.grpc.GrpcNotFoundException;
import com.robsil.model.exception.http.EntityNotFoundException;
import com.robsil.proto.*;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@GrpcService
public class MerchantStoreGrpcService extends MerchantStoreServiceGrpc.MerchantStoreServiceImplBase {

    private final MerchantStoreService merchantStoreService;
    private final MerchantService merchantService;

    @Override
    public void findByMerchantId(Id request, StreamObserver<MerchantStore> responseObserver) {
        try {
            var ms = merchantStoreService.findByMerchantId(request.getId());
            var me = ms.getMerchant();
            var merchant = Merchant.newBuilder()
                    .setId(me.getId())
                    .setUserId(me.getUserId())
                    .setFirstName(me.getFirstName())
                    .setLastName(me.getLastName())
                    .setPhoneNumber(me.getPhoneNumber())
                    .setEmail(me.getEmail())
                    .setIsNotBlocked(ms.isNotBlocked())
                    .build();
            responseObserver.onNext(MerchantStore.newBuilder()
                    .setMerchant(merchant)
                    .setName(ms.getName())
                    .setIsNotBlocked(ms.isNotBlocked())
                    .addAllContacts(ms.getContacts().stream().map(msc -> MerchantStoreContact.newBuilder()
                            .setName(msc.getName())
                            .setValue(msc.getValue()).build()
                    ).toList())
                    .build());
            responseObserver.onCompleted();
        } catch (EntityNotFoundException e) {
            throw new GrpcNotFoundException(e);
        }
    }

    @Override
    public void findById(Id request, StreamObserver<MerchantStore> responseObserver) {
        try {
            var ms = merchantStoreService.findById(request.getId());
            var me = ms.getMerchant();
            var merchant = Merchant.newBuilder()
                    .setId(me.getId())
                    .setUserId(me.getUserId())
                    .setFirstName(me.getFirstName())
                    .setLastName(me.getLastName())
                    .setPhoneNumber(me.getPhoneNumber())
                    .setEmail(me.getEmail())
                    .setIsNotBlocked(ms.isNotBlocked())
                    .build();
            responseObserver.onNext(MerchantStore.newBuilder()
                    .setMerchant(merchant)
                    .setName(ms.getName())
                    .setIsNotBlocked(ms.isNotBlocked())
                    .addAllContacts(ms.getContacts().stream().map(msc -> MerchantStoreContact.newBuilder()
                            .setName(msc.getName())
                            .setValue(msc.getValue()).build()
                    ).toList())
                    .build());
            responseObserver.onCompleted();
        } catch (EntityNotFoundException e) {
            throw new GrpcNotFoundException(e);
        }
    }
}
