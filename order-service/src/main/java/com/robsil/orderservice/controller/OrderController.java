package com.robsil.orderservice.controller;

import com.robsil.erommerce.jwtintegration.model.UserAuthenticationToken;
import com.robsil.orderservice.model.order.DetailedOrderDto;
import com.robsil.orderservice.model.order.OrderCreateRequest;
import com.robsil.orderservice.model.order.OrderDto;
import com.robsil.orderservice.model.orderitem.OrderItemDto;
import com.robsil.orderservice.service.OrderFacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderFacadeService orderFacadeService;

    @PostMapping
    public ResponseEntity<DetailedOrderDto> create(@RequestBody OrderCreateRequest request, UserAuthenticationToken user) {
        var result = orderFacadeService.create(user, request);
        return new ResponseEntity<>(DetailedOrderDto.builder()
                .order(new OrderDto(result.order().getId(),
                        result.order().getUserId(),
                        result.order().getStatus(),
                        result.order().getDetails()))

                .orderItems(result.items().stream()
                        .map(item -> new OrderItemDto(item.productId(), item.quantity()))
                        .toList())
                .build(), HttpStatus.CREATED);
    }
}
