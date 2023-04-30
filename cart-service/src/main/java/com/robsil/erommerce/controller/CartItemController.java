package com.robsil.erommerce.controller;

import com.robsil.erommerce.model.cartItem.CartItemChangeQuantityRequest;
import com.robsil.erommerce.model.cartItem.CartItemCreateRequest;
import com.robsil.erommerce.model.cartItem.CartItemDto;
import com.robsil.erommerce.model.cartItem.CartItemQuantityChangeResponse;
import com.robsil.erommerce.service.CartService;
import com.robsil.erommerce.service.facade.CartFacadeService;
import com.robsil.erommerce.service.facade.CartItemFacadeService;
import com.robsil.userservice.model.OAuth2User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cartItems")
public class CartItemController {

    private final CartFacadeService cartFacadeService;
    private final CartItemFacadeService cartItemFacadeService;
    private final CartService cartService;

//    private final CartItemDtoMapper cartItemDtoMapper;

    @PostMapping
    public ResponseEntity<CartItemDto> create(@RequestBody @Valid CartItemCreateRequest req,
                                              @AuthenticationPrincipal @NotNull OAuth2User user) {
//        return new ResponseEntity<>(cartItemDtoMapper.apply(cartItemFacadeService.addItem(principal, req)), HttpStatus.CREATED);
        cartItemFacadeService.addItem(user, req);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @PutMapping("/{cartItemId}/quantity")
    public ResponseEntity<CartItemQuantityChangeResponse> changeQuantity(@RequestBody @Valid CartItemChangeQuantityRequest req,
                                                                         @PathVariable Long cartItemId,
                                                                         @AuthenticationPrincipal @NotNull OAuth2User user) {
        return new ResponseEntity<>(cartItemFacadeService.changeQuantity(req, cartItemId, user), HttpStatus.OK);
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> delete(@PathVariable Long cartItemId,
                                       @AuthenticationPrincipal @NotNull OAuth2User user) {
        cartItemFacadeService.delete(cartItemId, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
