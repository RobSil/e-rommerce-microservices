package com.robsil.erommerce.service.facade;

import com.robsil.erommerce.jwtintegration.model.UserAuthenticationToken;
import com.robsil.erommerce.model.cart.DetailedCartDto;
import com.robsil.erommerce.service.CartItemService;
import com.robsil.erommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
public class CartFacadeService {

    private final CartService cartService;
    private final CartItemService cartItemService;

//    private final UserDtoMapper userDtoMapper;
//    private final ProductDtoMapper productDtoMapper;

    public DetailedCartDto findDetailedCartByUser(UserAuthenticationToken user) {
        var cart = cartService.findByUserId(user.getId());
        var cartItems = cartItemService.findAllByCartId(cart.getId());

        return null;
//        return DetailedCartDto.builder()
//                .cartDto(new CartDto(userDtoMapper.apply(cart.getUser())))
//                .items(cartItems.stream()
//                        .map(cartItem -> new MinimizedCartItemDto(productDtoMapper.apply(
//                                cartItem.getProduct()),
//                                cartItem.getQuantity(),
//                                cartItem.getPrice()))
//                        .collect(Collectors.toList()))
//                .build();
    }

    @Transactional
    public void deleteAll(UserAuthenticationToken user) {
        var cart = cartService.findByUserId(user.getId());

        cartItemService.deleteAllByCartId(cart.getId());
    }

}
