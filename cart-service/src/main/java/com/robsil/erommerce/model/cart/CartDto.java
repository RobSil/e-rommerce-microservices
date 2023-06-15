package com.robsil.erommerce.model.cart;

import com.robsil.erommerce.protoservice.util.model.User;
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
