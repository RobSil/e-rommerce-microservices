package com.robsil.orderservice.model.orderitem;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {

    private Long productId;
    private BigDecimal quantity;

}
