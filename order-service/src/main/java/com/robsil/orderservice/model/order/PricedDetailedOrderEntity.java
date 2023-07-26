package com.robsil.orderservice.model.order;

import com.robsil.orderservice.data.domain.Order;
import com.robsil.orderservice.model.orderitem.PricedOrderItemDto;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record PricedDetailedOrderEntity(
        Order order,
        List<PricedOrderItemDto> items,
        BigDecimal totalPrice
) {
}
