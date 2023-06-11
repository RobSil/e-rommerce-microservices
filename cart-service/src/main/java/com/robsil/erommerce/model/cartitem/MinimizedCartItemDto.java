package com.robsil.erommerce.model.cartitem;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MinimizedCartItemDto {

//    private Product product;

    private BigDecimal quantity;

    private BigDecimal price;

}
