package com.robsil.orderservice.data.repository;

import com.robsil.orderservice.data.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
