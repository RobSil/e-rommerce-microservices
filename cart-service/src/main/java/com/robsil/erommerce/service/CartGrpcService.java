package com.robsil.erommerce.service;

import com.robsil.erommerce.data.domain.Cart;
import com.robsil.erommerce.protoservice.util.converter.BigDecimalConverter;
import com.robsil.model.exception.grpc.GrpcNotFoundException;
import com.robsil.model.exception.http.EntityNotFoundException;
import com.robsil.proto.CartItem;
import com.robsil.proto.CartServiceGrpc;
import com.robsil.proto.Id;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartGrpcService extends CartServiceGrpc.CartServiceImplBase {

    private final CartService cartService;
    private final CartItemService cartItemService;

    @Override
    public void findByUserId(Id request, StreamObserver<com.robsil.proto.Cart> responseObserver) {
        Cart cart;
        try {
            cart = cartService.findByUserId(request.getId());
        } catch (EntityNotFoundException e) {
            responseObserver.onError(new GrpcNotFoundException(e));
            return;
        }

        var cartItems = cartItemService.findAllByCartId(cart.getId());

        responseObserver.onNext(com.robsil.proto.Cart.newBuilder()
                .setUserId(cart.getUserId())
                .addAllCartItems(cartItems.stream().map(item -> CartItem.newBuilder()
                                .setProductId(item.getProductId())
                                .setQuantity(BigDecimalConverter.toProto(item.getQuantity()))
                                .build())
                        .toList())
                .build());
        responseObserver.onCompleted();
    }
}
