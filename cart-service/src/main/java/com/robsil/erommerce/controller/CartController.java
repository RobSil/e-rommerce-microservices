package com.robsil.erommerce.controller;

import com.robsil.erommerce.jwtintegration.model.UserAuthenticationToken;
import com.robsil.erommerce.model.cart.DetailedCartDto;
import com.robsil.erommerce.service.facade.CartFacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/carts")
public class CartController {

    private final CartFacadeService cartFacadeService;

    @GetMapping
    public ResponseEntity<DetailedCartDto> getByCurrentUser(@AuthenticationPrincipal UserAuthenticationToken user) {
        return new ResponseEntity<>(cartFacadeService.findDetailedCartByUser(user), HttpStatus.OK);
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAllByCurrentUser(@AuthenticationPrincipal UserAuthenticationToken user) {
        cartFacadeService.deleteAll(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
