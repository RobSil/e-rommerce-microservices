package com.robsil.erommerce.model.cart;

import com.robsil.proto.User;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CartDto {

    private User user;

}
