package com.robsil.orderservice.service;

import com.robsil.orderservice.data.domain.Order;
import com.robsil.orderservice.data.domain.OrderItem;
import com.robsil.orderservice.model.orderitem.OrderItemCreateRequest;

import java.util.List;

public interface OrderItemService {

    OrderItem create(Order order, OrderItemCreateRequest request);
    List<OrderItem> create(Order order, List<OrderItemCreateRequest> requests);
}
