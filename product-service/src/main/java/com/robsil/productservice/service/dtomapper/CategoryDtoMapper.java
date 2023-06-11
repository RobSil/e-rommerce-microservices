package com.robsil.productservice.service.dtomapper;

import com.robsil.productservice.data.domain.Category;
import com.robsil.productservice.model.category.CategoryDto;
import com.robsil.productservice.service.CategoryService;
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
