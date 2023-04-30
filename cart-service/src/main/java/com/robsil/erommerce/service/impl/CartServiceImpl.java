package com.robsil.erommerce.service.impl;

import com.robsil.erommerce.data.domain.Cart;
import com.robsil.erommerce.data.repository.CartRepository;
import com.robsil.erommerce.service.CartService;
import com.robsil.model.exception.http.EntityNotFoundException;
import com.robsil.proto.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    private Cart saveEntity(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public Cart findById(Long id) {
        var cart = cartRepository.findById(id);

        if (cart.isEmpty()) {
            throw new EntityNotFoundException("Cart not found.");
        }

        return cart.get();
    }

    @Transactional
    @Override
    public Cart findByUserId(OAuth2AuthenticatedPrincipal principal) {
        var cart = cartRepository.findByUserId(principal.getAttribute("id"));

        if (cart.isEmpty()) {
            cart = Optional.of(createDefault(principal));
        }

        return cart.get();
    }

    private Cart createDefault(OAuth2AuthenticatedPrincipal principal) {

        if (principal.getAttribute("id") == null || principal.getAttribute("id") <= 0) {
            throw new IllegalArgumentException("Impossible value provided.");
        }

        var cart = Cart.builder()
                .user(user)
                .build();

        return saveEntity(cart);
    }
}
