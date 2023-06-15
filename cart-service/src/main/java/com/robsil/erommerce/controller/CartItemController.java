package com.robsil.erommerce.controller;

import com.robsil.erommerce.jwtintegration.model.UserAuthenticationToken;
import com.robsil.erommerce.model.cartitem.CartItemChangeQuantityRequest;
import com.robsil.erommerce.model.cartitem.CartItemCreateRequest;
import com.robsil.erommerce.model.cartitem.CartItemDto;
import com.robsil.erommerce.model.cartitem.CartItemQuantityChangeResponse;
import com.robsil.erommerce.service.CartService;
import com.robsil.erommerce.service.facade.CartFacadeService;
import com.robsil.erommerce.service.facade.CartItemFacadeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cartItems")
public class CartItemController {

    private final CartFacadeService cartFacadeService;
    private final CartItemFacadeService cartItemFacadeService;
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartItemDto> create(@RequestBody @Valid CartItemCreateRequest req,
                                              @NotNull UserAuthenticationToken user) {
        return new ResponseEntity<>(cartItemFacadeService.addItemController(user, req), HttpStatus.CREATED);
    }

    @PutMapping("/{cartItemId}/quantity")
    public ResponseEntity<CartItemQuantityChangeResponse> changeQuantity(@RequestBody @Valid CartItemChangeQuantityRequest req,
                                                                         @PathVariable Long cartItemId,
                                                                         @NotNull UserAuthenticationToken user) {
        return new ResponseEntity<>(cartItemFacadeService.changeQuantity(req, cartItemId, user), HttpStatus.OK);
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> delete(@PathVariable Long cartItemId,
                                       @NotNull UserAuthenticationToken user) {
        cartItemFacadeService.delete(cartItemId, user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
