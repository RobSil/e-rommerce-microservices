package com.robsil.erommerce.protoservice.util.converter;

import com.robsil.erommerce.protoservice.util.model.Product;

public class ProductConverter {

    private ProductConverter() {
    }

    public static Product toJava(com.robsil.proto.Product product) {
        return new Product(product.getId(),
                BigDecimalConverter.toJava(product.getPrice()),
                product.getName(),
                product.getSku());
    }

}
