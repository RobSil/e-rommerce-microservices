package com.robsil.productservice.service;

import com.robsil.erommerce.protoservice.util.converter.BigDecimalConverter;
import com.robsil.model.exception.grpc.GrpcNotFoundException;
import com.robsil.model.exception.http.EntityNotFoundException;
import com.robsil.proto.*;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@GrpcService
public class ProductGrpcService extends ProductServiceGrpc.ProductServiceImplBase {

    private final ProductService productService;

    @Override
    public void findById(Id request, StreamObserver<Product> responseObserver) {
        try {
            var product = productService.findById(request.getId());
            responseObserver.onNext(Product.newBuilder()
                    .setId(product.getId())
                    .setPrice(BigDecimalConverter.toProto(product.getPrice()))
                    .setName(product.getName())
                    .setSku(product.getSku())
                    .build());
            responseObserver.onCompleted();
        } catch (EntityNotFoundException e) {
            responseObserver.onError(new GrpcNotFoundException(e));
        }
    }

    @Override
    public void findAllByIdIn(IdArray request, StreamObserver<ProductMap> responseObserver) {
        responseObserver.onNext(ProductMap.newBuilder()
                .putAllProducts(productService.findAllByIdIn(request.getIdsList())
                        .stream()
                        .map(product -> Product.newBuilder()
                                .setId(product.getId())
                                .setPrice(BigDecimalConverter.toProto(product.getPrice()))
                                .setName(product.getName())
                                .setSku(product.getSku())
                                .build())
                        .collect(Collectors.toMap(Product::getId, product -> product)))
                .build());
        responseObserver.onCompleted();
    }
}
