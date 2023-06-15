package com.robsil.erommerce.protoservice.util.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    private Long id;
    private BigDecimal price;
    private String name;
    private String sku;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Product() {}

    public Product(Long id, BigDecimal price, String name, String sku) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.sku = sku;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(price, product.price) && Objects.equals(name, product.name) && Objects.equals(sku, product.sku);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, name, sku);
    }
}
