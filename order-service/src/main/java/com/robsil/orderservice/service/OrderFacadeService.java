package com.robsil.orderservice.service;

import com.robsil.erommerce.jwtintegration.model.UserAuthenticationToken;
import com.robsil.erommerce.model.MailDto;
import com.robsil.erommerce.model.RecipientType;
import com.robsil.erommerce.protoservice.util.converter.BigDecimalConverter;
import com.robsil.erommerce.service.MailingService;
import com.robsil.orderservice.model.order.DetailedOrderEntity;
import com.robsil.orderservice.model.order.OrderCreateRequest;
import com.robsil.orderservice.model.order.PricedDetailedOrderEntity;
import com.robsil.orderservice.model.orderitem.OrderItemCreateRequest;
import com.robsil.orderservice.model.orderitem.PricedOrderItemDto;
import com.robsil.proto.CartServiceGrpc;
import com.robsil.proto.Id;
import com.robsil.proto.ProductServiceGrpc;
import com.robsil.proto.UserServiceGrpc;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.apache.http.entity.ContentType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

@Service
@Log4j2
@RequiredArgsConstructor
public class OrderFacadeService {

    private final OrderService orderService;
    private final OrderItemService orderItemService;
    private final MailingService mailingService;

    @GrpcClient("cart-service")
    private CartServiceGrpc.CartServiceBlockingStub cartService;
    @GrpcClient("user-service")
    private UserServiceGrpc.UserServiceBlockingStub userService;
    @GrpcClient("product-service")
    private ProductServiceGrpc.ProductServiceBlockingStub productService;

    @Transactional
    public PricedDetailedOrderEntity create(UserAuthenticationToken userToken, OrderCreateRequest request) {
        var user = userService.findById(Id.newBuilder().setId(userToken.getId()).build());
        var cart = cartService.findByUserId(Id.newBuilder().setId(userToken.getId()).build());
        var order = orderService.create(userToken, request);
        var orderItems = orderItemService.create(order, cart.getCartItemsList()
                        .stream()
                        .map(item -> new OrderItemCreateRequest(item.getProductId(),
                                BigDecimalConverter.toJava(item.getQuantity())))
                        .toList())
                .stream()
                .map(item -> PricedOrderItemDto.builder()
                        .productId(item.getProductId())
                        .quantity(item.getQuantity())
                        .price(BigDecimalConverter.toJava(productService.findById(Id.newBuilder().setId(item.getProductId()).build()).getPrice()))
                        .build())
                .toList();


        //noinspection ResultOfMethodCallIgnored
        cartService.deleteAllByUserId(Id.newBuilder().setId(userToken.getId()).build());

        mailingService.sendMail(MailDto.builder()
                .sendTo(List.of(user.getEmail()))
                .recipientType(RecipientType.TO)
                .content("You have successfully ordered!")
                .subject("Successful Order")
                .contentType(ContentType.TEXT_HTML.getMimeType())
                .build());

        return PricedDetailedOrderEntity.builder()
                .order(order)
                .items(orderItems)
                .totalPrice(orderItems
                        .stream()
                        .reduce(BigDecimal.ZERO, ((bigDecimal, pricedOrderItemDto) -> bigDecimal.add(pricedOrderItemDto.price())), (BigDecimal::add)))
                .build();
    }

}
