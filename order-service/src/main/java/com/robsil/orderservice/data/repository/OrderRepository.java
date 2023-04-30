package com.robsil.orderservice.data.repository;

import com.robsil.orderservice.data.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select order from Order order where order.userId = :userId")
    List<Order> findAllByUserId(Long userId);

    @Query("select order from Order order where order.userId = :userId")
    Page<Order> findAllByUserId(Long userId, Pageable pageable);

}
