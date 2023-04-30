package com.robsil.orderservice.data.domain;


import com.robsil.orderservice.model.OrderStatus;
import com.robsil.orderservice.model.order.OrderDetails;
import com.robsil.userservice.data.domain.BaseEntity;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

//    @ManyToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Long userId;

    @Column
    @Enumerated(value = EnumType.STRING)
    private OrderStatus status;

    @Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb")
    private OrderDetails details;

}
