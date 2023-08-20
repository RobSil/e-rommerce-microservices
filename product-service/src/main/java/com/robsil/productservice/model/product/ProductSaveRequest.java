package com.robsil.productservice.model.product;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductSaveRequest(
        @NotNull
        @Positive
        Long id,
        @NotNull
        @Positive
        Long categoryId,
        @NotEmpty
        String name,
        @NotEmpty
        String sku,
        @NotNull
        BigDecimal price,
        BigDecimal quantity,
        MeasureUnit measureUnit,
        @NotNull
        ProductStatus status,
        boolean isActive
) {
}
