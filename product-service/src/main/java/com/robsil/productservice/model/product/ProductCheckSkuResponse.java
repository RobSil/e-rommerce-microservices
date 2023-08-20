package com.robsil.productservice.model.product;

import lombok.Builder;

@Builder
public record ProductCheckSkuResponse(
        boolean isExists,
//    not null, if isExists == true
        ProductDto product
) {
}
