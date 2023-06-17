package com.robsil.orderservice.model.order;

import com.robsil.orderservice.data.domain.Order;
import com.robsil.orderservice.data.domain.OrderItem;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetailedOrderEntity {

    private Order order;
    private List<OrderItem> orderItems;

}
