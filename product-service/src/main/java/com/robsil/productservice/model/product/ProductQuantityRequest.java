package com.robsil.productservice.model.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductQuantityRequest(
        @NotNull
        @Positive
        Long productId,
        @NotNull
        @Size(min = 0)
        BigDecimal quantity
) {
}
