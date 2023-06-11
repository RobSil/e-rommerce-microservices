package com.robsil.erommerce.service.facade;

import com.robsil.erommerce.data.domain.CartItem;
import com.robsil.erommerce.jwtintegration.model.UserAuthenticationToken;
import com.robsil.erommerce.model.cartitem.CartItemChangeQuantityRequest;
import com.robsil.erommerce.model.cartitem.CartItemCreateRequest;
import com.robsil.erommerce.model.cartitem.CartItemQuantityChangeResponse;
import com.robsil.erommerce.service.CartItemService;
import com.robsil.erommerce.service.CartService;
import com.robsil.model.exception.http.ForbiddenException;
import com.robsil.proto.Id;
import com.robsil.proto.ProductServiceGrpc;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartItemFacadeService {

    private final CartItemService cartItemService;
    // TODO: 10.06.2023 inject stub
    @GrpcClient("product-service")
    private ProductServiceGrpc.ProductServiceBlockingStub productService;
    private final CartService cartService;

    @Transactional
    public CartItem addItem(UserAuthenticationToken user, CartItemCreateRequest req) {
        var product = productService.findById(Id.newBuilder().setId(req.getProductId()).build());
        var cart = cartService.findByUserId(user.getId());

        return cartItemService.create(cart, product, req.getQuantity());
    }

    public CartItemQuantityChangeResponse changeQuantity(CartItemChangeQuantityRequest req,
                                                         Long cartItemId,
                                                         UserAuthenticationToken principal) {
        var cartItem = cartItemService.findById(cartItemId);

        if (!cartItem.getCart().getUserId().equals(principal.getId())) {
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
    public void delete(Long cartItemId, UserAuthenticationToken principal) {
        var cartItem = cartItemService.findById(cartItemId);

        if (cartItem.getCart().getUserId().equals(principal.getId())) {
            throw new ForbiddenException("Can't delete foreign from cart.");
        }

        cartItemService.deleteById(cartItem.getId());
    }

}
