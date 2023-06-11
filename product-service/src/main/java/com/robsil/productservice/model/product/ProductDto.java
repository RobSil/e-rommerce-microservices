package com.robsil.productservice.model.product;

import com.robsil.productservice.model.category.CategoryDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ProductDto {

    @NotEmpty
    private Long id;

    @NotEmpty
    private CategoryDto category;

    @NotEmpty
    private String name;

    @NotEmpty
    private String sku;

    @NotNull
    private BigDecimal price;

    private BigDecimal quantity;

    private String measureUnit;

    @NotNull
    private ProductStatus status;

    private boolean isActive;

}
