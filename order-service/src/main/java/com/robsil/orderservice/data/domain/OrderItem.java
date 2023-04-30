package com.robsil.orderservice.data.domain;

import com.robsil.userservice.data.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_items")
public class OrderItem extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

//    @ManyToOne
//    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Long productId;

    @Column
    private BigDecimal quantity;

}
