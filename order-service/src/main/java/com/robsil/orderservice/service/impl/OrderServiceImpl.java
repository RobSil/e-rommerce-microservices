package com.robsil.orderservice.service.impl;

import com.robsil.erommerce.jwtintegration.model.UserAuthenticationToken;
import com.robsil.model.exception.http.EntityNotFoundException;
import com.robsil.orderservice.data.domain.Order;
import com.robsil.orderservice.data.repository.OrderRepository;
import com.robsil.orderservice.model.OrderStatus;
import com.robsil.orderservice.model.order.OrderCreateRequest;
import com.robsil.orderservice.model.order.OrderDetails;
import com.robsil.orderservice.service.OrderService;
import com.robsil.proto.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private Order saveEntity(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order findById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
    }

    @Override
    public List<Order> findAllByUserId(Long userId) {
        return orderRepository.findAllByUserId(userId);
    }

    @Override
    public Page<Order> findAllByUserId(Long userId, Pageable pageable) {
        return orderRepository.findAllByUserId(userId, pageable);
    }

    @Override
    @Transactional
    public Order create(UserAuthenticationToken user, OrderCreateRequest request) {
        var order = Order.builder()
                .userId(user.getId())
                .status(request.getStatus())
                .details(request.getDetails())
                .build();

        order = saveEntity(order);

        return order;
    }

    @Override
    @Transactional
    public Order changeStatus(Order order, OrderStatus newStatus) {
        order.setStatus(newStatus);
        order = saveEntity(order);

        return order;
    }

    @Override
    @Transactional
    public Order changeStatus(Long orderId, OrderStatus newStatus) {
        return changeStatus(findById(orderId), newStatus);
    }
}
