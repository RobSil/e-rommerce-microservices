package com.robsil.erommerce.service.facade;

import com.robsil.erommerce.data.domain.CartItem;
import com.robsil.erommerce.jwtintegration.model.UserAuthenticationToken;
import com.robsil.erommerce.model.cart.DetailedCartDto;
import com.robsil.erommerce.model.cartitem.MinimizedCartItemDto;
import com.robsil.erommerce.protoservice.util.converter.ProductConverter;
import com.robsil.erommerce.protoservice.util.converter.UserConverter;
import com.robsil.erommerce.service.CartItemService;
import com.robsil.erommerce.service.CartService;
import com.robsil.proto.Id;
import com.robsil.proto.IdArray;
import com.robsil.proto.ProductServiceGrpc;
import com.robsil.proto.UserServiceGrpc;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;

@Service
@Log4j2
@RequiredArgsConstructor
public class CartFacadeService {

    private final CartService cartService;
    private final CartItemService cartItemService;

    @GrpcClient("product-service")
    private ProductServiceGrpc.ProductServiceBlockingStub productServiceStub;
    @GrpcClient("user-service")
    private UserServiceGrpc.UserServiceBlockingStub userServiceStub;

    public DetailedCartDto findDetailedCartByUser(UserAuthenticationToken user) {
        var cart = cartService.findByUserId(user.getId());
        var cartItems = cartItemService.findAllByCartId(cart.getId());

        var productIds = cartItems.stream().map(CartItem::getProductId).toList();
        var products = productServiceStub.findAllByIdIn(IdArray.newBuilder().addAllIds(productIds).build()).getProductsMap();

        var totalAmount = BigDecimal.ZERO;
        var cartItemsDtos = new ArrayList<MinimizedCartItemDto>();

        for (CartItem item : cartItems) {
            var product = ProductConverter.toJava(products.get(item.getProductId()));
            totalAmount = totalAmount.add(item.getQuantity().multiply(product.getPrice()));

            cartItemsDtos.add(MinimizedCartItemDto.builder()
                    .id(item.getId())
                    .quantity(item.getQuantity())
                    .product(product)
                    .price(item.getQuantity().multiply(product.getPrice()))
                    .build());
        }

        return DetailedCartDto.builder()
                .user(UserConverter.toJava(userServiceStub.findById(Id.newBuilder().setId(user.getId()).build())))
                .items(cartItemsDtos)
                .totalAmount(totalAmount)
                .build();
    }

    @Transactional
    public void deleteAll(UserAuthenticationToken user) {
        var cart = cartService.findByUserId(user.getId());

        cartItemService.deleteAllByCartId(cart.getId());
    }

}
