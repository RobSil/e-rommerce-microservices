package com.robsil.erommerce.data.repository;

import com.robsil.erommerce.data.domain.CartItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Query("select cartItem from CartItem cartItem where cartItem.cart.id = :cartId")
    List<CartItem> findAllByCartId(Long cartId);

    @Query("select cartItem from CartItem cartItem where cartItem.cart.id = :cartId")
    Page<CartItem> findAllByCartId(Long cartId, Pageable pageable);

    @Modifying
    @Query("delete from CartItem cartItem where cartItem.cart.id = :cartId")
    void deleteAllByCartId(Long cartId);

    @Modifying
    @Query("update CartItem cartItem set cartItem.quantity = :quantity where cartItem.id = :cartItemId")
    void changeQuantity(Long cartItemId, BigDecimal quantity);

}
