package com.robsil.orderservice.service;

import com.robsil.orderservice.data.domain.Order;
import com.robsil.orderservice.model.OrderStatus;
import com.robsil.orderservice.model.order.OrderDetails;
import com.robsil.proto.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {

    Order findById(Long orderId);
    List<Order> findAllByUserId(Long userId);
    Page<Order> findAllByUserId(Long userId, Pageable pageable);
    Order create(User user, OrderStatus status, OrderDetails details);
    Order changeStatus(Order order, OrderStatus newStatus);
    Order changeStatus(Long orderId, OrderStatus newStatus);

}
