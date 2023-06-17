package com.robsil.orderservice.model.order;

import com.robsil.orderservice.model.OrderStatus;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long id;
    private Long userId;
    private OrderStatus status;
    private OrderDetails details;

}
