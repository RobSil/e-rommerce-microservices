package com.robsil.erommerce.model.cart;

import com.robsil.erommerce.model.cartItem.MinimizedCartItemDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class DetailedCartDto {

    private CartDto cartDto;

    private List<MinimizedCartItemDto> items;

}
