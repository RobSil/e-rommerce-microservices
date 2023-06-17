package com.robsil.orderservice.model.orderitem;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemCreateRequest {

    private Long productId;
    private BigDecimal quantity;

}
