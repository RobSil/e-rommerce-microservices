package com.robsil.productservice.service;

import com.robsil.erommerce.protoservice.util.BigDecimalConverter;
import com.robsil.model.exception.grpc.GrpcNotFoundException;
import com.robsil.model.exception.http.EntityNotFoundException;
import com.robsil.proto.Id;
import com.robsil.proto.Product;
import com.robsil.proto.ProductServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.stereotype.Service;

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
                    .build());
            responseObserver.onCompleted();
        } catch (EntityNotFoundException e) {
            responseObserver.onError(new GrpcNotFoundException(e));
        }
        super.findById(request, responseObserver);
    }
}
