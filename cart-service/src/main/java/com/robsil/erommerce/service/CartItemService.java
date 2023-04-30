package com.robsil.erommerce.service;

import com.robsil.erommerce.data.domain.Cart;
import com.robsil.erommerce.data.domain.CartItem;
import com.robsil.proto.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface CartItemService {

    CartItem findById(Long cartItemId);
    List<CartItem> findAllByCartId(Long cartId);
    Page<CartItem> findAllByCartId(Long cartId, Pageable pageable);
    CartItem create(Cart cart, Product product, BigDecimal quantity);
    void changeQuantity(Long cartItemId, BigDecimal quantity);
    void deleteById(Long cartItemId);
    void deleteAllByCartId(Long cartId);

}
