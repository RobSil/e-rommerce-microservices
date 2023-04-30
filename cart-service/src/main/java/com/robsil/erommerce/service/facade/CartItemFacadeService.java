package com.robsil.erommerce.service.facade;

import com.robsil.erommerce.data.domain.CartItem;
import com.robsil.erommerce.model.cartItem.CartItemChangeQuantityRequest;
import com.robsil.erommerce.model.cartItem.CartItemCreateRequest;
import com.robsil.erommerce.model.cartItem.CartItemQuantityChangeResponse;
import com.robsil.erommerce.service.CartItemService;
import com.robsil.erommerce.service.CartService;
import com.robsil.model.exception.http.ForbiddenException;
import com.robsil.proto.Id;
import com.robsil.proto.ProductServiceGrpc;
import com.robsil.userservice.model.OAuth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartItemFacadeService {

    private final CartItemService cartItemService;
    private final ProductServiceGrpc.ProductServiceBlockingStub productService;
    private final CartService cartService;

    @Transactional
    public CartItem addItem(OAuth2User user, CartItemCreateRequest req) {
        var product = productService.findById(Id.newBuilder().setId(req.getProductId()).build());
        var cart = cartService.findByUserId(user.getId());

        return cartItemService.create(cart, product, req.getQuantity());
    }

    public CartItemQuantityChangeResponse changeQuantity(CartItemChangeQuantityRequest req,
                                                         Long cartItemId,
                                                         OAuth2AuthenticatedPrincipal principal) {
        var cartItem = cartItemService.findById(cartItemId);

        if (!cartItem.getCart().getUserId().equals(principal.getAttribute("id"))) {
            throw new ForbiddenException("Can't change quantity of item from foreign cart.");
        }

        var oldQuantity = cartItem.getQuantity();

        cartItemService.changeQuantity(cartItem.getId(), req.getNewQuantity());

        return CartItemQuantityChangeResponse.builder()
                .message("Success")
                .difference(cartItem.getQuantity().subtract(oldQuantity))
                .newQuantity(req.getNewQuantity())
                .build();
    }

    @Transactional
    public void delete(Long cartItemId, OAuth2AuthenticatedPrincipal principal) {
        var cartItem = cartItemService.findById(cartItemId);

        if (cartItem.getCart().getUserId().equals(principal.getAttribute("id"))) {
            throw new ForbiddenException("Can't delete foreign from cart.");
        }

        cartItemService.deleteById(cartItem.getId());
    }

}
