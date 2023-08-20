package com.robsil.productservice.model.product;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductCreateRequest(
        @NotNull
        @Positive
        Long categoryId,
        @NotEmpty
        String name,
        @NotEmpty
        String sku,
        @NotNull
        @Positive
        BigDecimal price,
        @NotNull
        BigDecimal quantity,
        @NotNull
        MeasureUnit measureUnit,
        @NotNull
        ProductStatus status,
        boolean isActive,
        Long merchantStoreId
) {
}
