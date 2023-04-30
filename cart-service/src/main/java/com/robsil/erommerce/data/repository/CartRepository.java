package com.robsil.erommerce.data.repository;

import com.robsil.erommerce.data.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("select cart from Cart cart where cart.user.id = :userId")
    Optional<Cart> findByUserId(Long userId);

}
