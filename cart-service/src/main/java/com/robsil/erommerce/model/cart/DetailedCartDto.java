package com.robsil.erommerce.model.cart;

import com.robsil.erommerce.model.cartitem.MinimizedCartItemDto;
import com.robsil.erommerce.protoservice.util.model.User;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class DetailedCartDto {

    private User user;

    private BigDecimal totalAmount;

    private List<MinimizedCartItemDto> items;

}
