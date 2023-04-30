package com.robsil.cartservice.service.dtoMapper;

import com.robsil.cartservice.data.domain.Category;
import com.robsil.cartservice.model.category.CategoryDto;
import com.robsil.cartservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class CategoryDtoMapper implements Function<Category, CategoryDto> {

    private final CategoryService categoryService;

    @Override
    public CategoryDto apply(Category category) {

        if (category == null) {
            throw new IllegalArgumentException("Category can't be null");
        }

        var result = CategoryDto.builder()
                .id(category.getId())
                .title(category.getTitle())
                .parent(null)
                .build();

        if (category.getParent() != null) {
            result.setParent(this.apply(category));
        }

        return result;
    }
}
