package com.robsil.productservice.model.product;

import jakarta.validation.constraints.NotEmpty;
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
public class ProductSaveRequest {

    @NotEmpty
    private Long id;

    @NotEmpty
    private Long categoryId;

    @NotEmpty
    private String name;

    @NotEmpty
    private String sku;

    @NotNull
    private BigDecimal price;

    private BigDecimal quantity;

    private MeasureUnit measureUnit;

    @NotNull
    private ProductStatus status;

    private boolean isActive;

}
