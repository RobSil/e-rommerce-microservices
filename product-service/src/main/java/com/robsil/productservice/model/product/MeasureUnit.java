package com.robsil.productservice.model.product;

public enum MeasureUnit {
    PIECE("pcs");

    private final String value;

    MeasureUnit(String value) {
        this.value = value;
    }
}
