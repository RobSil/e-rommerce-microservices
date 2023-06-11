package com.robsil.erommerce.model.cartitem;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CartItemChangeQuantityRequest {

    @NotNull
    private BigDecimal newQuantity;

}
