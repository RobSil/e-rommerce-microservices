package com.robsil.erommerce.model.cartitem;

import com.robsil.erommerce.model.cart.CartDto;
import com.robsil.erommerce.protoservice.util.model.Product;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CartItemDto {

    private CartDto cart;

    private Long id;

    private Product product;

    private BigDecimal quantity;

    private BigDecimal price;

}
