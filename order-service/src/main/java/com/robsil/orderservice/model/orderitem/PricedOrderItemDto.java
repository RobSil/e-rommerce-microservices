package com.robsil.orderservice.model.orderitem;


import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PricedOrderItemDto(
        Long productId,
        BigDecimal quantity,
        BigDecimal price
) {
}
