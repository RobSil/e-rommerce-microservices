package com.robsil.orderservice.service.impl;

import com.robsil.orderservice.data.domain.Order;
import com.robsil.orderservice.data.domain.OrderItem;
import com.robsil.orderservice.data.repository.OrderItemRepository;
import com.robsil.orderservice.model.orderitem.OrderItemCreateRequest;
import com.robsil.orderservice.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Override
    public OrderItem create(Order order, OrderItemCreateRequest request) {
        var item = new OrderItem(order, request.getProductId(), request.getQuantity());
        item = orderItemRepository.save(item);
        return item;
    }

    @Override
    public List<OrderItem> create(Order order, List<OrderItemCreateRequest> requests) {
        var result = new ArrayList<OrderItem>();

        requests.forEach(item -> result.add(create(order, item)));

        return result;
    }
}
