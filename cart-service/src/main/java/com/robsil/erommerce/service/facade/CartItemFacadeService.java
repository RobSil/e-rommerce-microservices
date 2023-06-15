package com.robsil.erommerce.service.facade;

import com.robsil.erommerce.data.domain.CartItem;
import com.robsil.erommerce.jwtintegration.model.UserAuthenticationToken;
import com.robsil.erommerce.model.cart.CartDto;
import com.robsil.erommerce.model.cartitem.CartItemChangeQuantityRequest;
import com.robsil.erommerce.model.cartitem.CartItemCreateRequest;
import com.robsil.erommerce.model.cartitem.CartItemDto;
import com.robsil.erommerce.model.cartitem.CartItemQuantityChangeResponse;
import com.robsil.erommerce.protoservice.util.converter.BigDecimalConverter;
import com.robsil.erommerce.protoservice.util.converter.ProductConverter;
import com.robsil.erommerce.protoservice.util.converter.UserConverter;
import com.robsil.erommerce.service.CartItemService;
import com.robsil.erommerce.service.CartService;
import com.robsil.model.exception.http.ForbiddenException;
import com.robsil.model.exception.http.HttpConflictException;
import com.robsil.proto.Id;
import com.robsil.proto.ProductServiceGrpc;
import com.robsil.proto.UserServiceGrpc;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartItemFacadeService {

    private final CartItemService cartItemService;
    private final CartService cartService;

    @GrpcClient("product-service")
    private ProductServiceGrpc.ProductServiceBlockingStub productService;
    @GrpcClient("user-service")
    private UserServiceGrpc.UserServiceBlockingStub userServiceStub;

    CartItemDto toDto(CartItem item) {
        var cart = item.getCart();
        var product = productService.findById(Id.newBuilder().setId(item.getProductId()).build());
        return CartItemDto.builder()
                .cart(CartDto.builder().user(UserConverter.toJava(userServiceStub.findById(Id.newBuilder().setId(cart.getUserId()).build()))).build())
                .id(item.getId())
                .product(ProductConverter.toJava(product))
                .quantity(item.getQuantity())
                .price(item.getQuantity().multiply(BigDecimalConverter.toJava(product.getPrice())))
                .build();
    }

    @Transactional
    public CartItemDto addItemController(UserAuthenticationToken user, CartItemCreateRequest req) {
        var item = addItem(user, req);
        return toDto(item);
    }

    @Transactional
    public CartItem addItem(UserAuthenticationToken user, CartItemCreateRequest req) {
        var product = productService.findById(Id.newBuilder().setId(req.getProductId()).build());
        var cart = cartService.findByUserId(user.getId());

        var cartItem = cartItemService.findAllByCartId(cart.getId()).stream()
                .filter(item -> item.getProductId().equals(product.getId()))
                .findFirst();

        if (cartItem.isPresent()) {
            throw new HttpConflictException("Product in this cart is already exist.");
        }

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

        cartItem = cartItemService.changeQuantity(cartItem, req.getNewQuantity());

        var product = productService.findById(Id.newBuilder().setId(cartItem.getProductId()).build());

        return CartItemQuantityChangeResponse.builder()
                .message("Success")
                .difference(cartItem.getQuantity().subtract(oldQuantity))
                .newQuantity(req.getNewQuantity())
                .price(BigDecimalConverter.toJava(product.getPrice()).multiply(cartItem.getQuantity()))
                .build();
    }


    @Transactional
    public void delete(Long cartItemId, UserAuthenticationToken user) {
        var cartItem = cartItemService.findById(cartItemId);

        if (!cartItem.getCart().getUserId().equals(user.getId())) {
            throw new ForbiddenException("Can't delete foreign from cart.");
        }

        cartItemService.deleteById(cartItem.getId());
    }

}
