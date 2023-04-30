package com.robsil.cartservice.model.category;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CategoryDto {

    private Long id;

    private CategoryDto parent;

    private String title;

}
