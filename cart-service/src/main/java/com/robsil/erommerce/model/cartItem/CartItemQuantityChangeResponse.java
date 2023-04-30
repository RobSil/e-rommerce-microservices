package com.robsil.erommerce.model.cartItem;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CartItemQuantityChangeResponse {

    private String message;

    private BigDecimal difference;

    @JsonProperty("new_quantity")
    private BigDecimal newQuantity;

}
