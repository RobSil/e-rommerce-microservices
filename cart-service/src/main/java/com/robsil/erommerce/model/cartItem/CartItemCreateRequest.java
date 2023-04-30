package com.robsil.erommerce.model.cartItem;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CartItemCreateRequest {

    @NotNull
    @Positive
    private Long productId;

    @NotNull
    @Positive
    private BigDecimal quantity;

}
