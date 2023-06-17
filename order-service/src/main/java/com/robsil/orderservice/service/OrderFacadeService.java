package com.robsil.orderservice.service;

import com.robsil.erommerce.jwtintegration.model.UserAuthenticationToken;
import com.robsil.erommerce.protoservice.util.converter.BigDecimalConverter;
import com.robsil.orderservice.model.order.DetailedOrderEntity;
import com.robsil.orderservice.model.order.OrderCreateRequest;
import com.robsil.orderservice.model.orderitem.OrderItemCreateRequest;
import com.robsil.proto.CartServiceGrpc;
import com.robsil.proto.Id;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
public class OrderFacadeService {

    private final OrderService orderService;
    private final OrderItemService orderItemService;

    @GrpcClient("cart-service")
    private CartServiceGrpc.CartServiceBlockingStub cartService;

    @Transactional
    public DetailedOrderEntity create(UserAuthenticationToken user, OrderCreateRequest request) {
        var cart = cartService.findByUserId(Id.newBuilder().setId(user.getId()).build());
        var order = orderService.create(user, request);
        var orderItems = orderItemService.create(order, cart.getCartItemsList().stream()
                .map(item -> new OrderItemCreateRequest(item.getProductId(), BigDecimalConverter.toJava(item.getQuantity())))
                .toList());

        cartService.deleteAllByUserId(Id.newBuilder().setId(user.getId()).build());

        return DetailedOrderEntity.builder()
                .order(order)
                .orderItems(orderItems)
                .build();
    }

}
