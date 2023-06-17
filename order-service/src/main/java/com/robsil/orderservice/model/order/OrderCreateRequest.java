package com.robsil.orderservice.model.order;

import com.robsil.orderservice.model.OrderStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateRequest {

    private OrderDetails details;
    private OrderStatus status;

}
