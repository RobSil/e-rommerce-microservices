package com.robsil.orderservice.model.order;

import com.robsil.orderservice.model.orderitem.OrderItemDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetailedOrderDto {

    private OrderDto order;
    private List<OrderItemDto> orderItems;

}
