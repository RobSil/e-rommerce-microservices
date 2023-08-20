package com.robsil.productservice.data.domain;

import com.robsil.productservice.model.product.MeasureUnit;
import com.robsil.productservice.model.product.ProductStatus;
import com.robsil.userservice.data.domain.BaseEntity;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product extends BaseEntity {

//    @Indexed
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @Column
    private String name;

    @Column
    private String sku;

    @Column
    private BigDecimal price;

    @Column
    private BigDecimal quantity;

    @Column(name = "measure_unit")
    @Enumerated(EnumType.STRING)
    private MeasureUnit measureUnit;

    @Column
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @Column(name = "is_active")
    private boolean isActive;

    @Builder.Default
    @Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> properties = new HashMap<>();

    @Column(name = "merchant_store_id")
    private Long merchantStoreId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(quantity, product.quantity) && isActive == product.isActive && Objects.equals(super.getId(), product.getId()) && Objects.equals(name, product.name) && Objects.equals(sku, product.sku) && Objects.equals(price, product.price) && status == product.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getId(), name, sku, price, quantity, status, isActive);
    }
}
