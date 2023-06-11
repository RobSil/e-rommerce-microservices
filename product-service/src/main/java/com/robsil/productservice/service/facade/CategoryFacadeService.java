package com.robsil.productservice.service.facade;

import com.robsil.productservice.data.domain.Category;
import com.robsil.productservice.service.CategoryService;
import com.robsil.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class CategoryFacadeService {

    private final CategoryService categoryService;
    private final ProductService productService;

    public void deleteById(Long categoryId) {
        var category = categoryService.findById(categoryId);

        deleteById(category);
    }

    private void deleteById(Category category) {
        if (category == null) {
            return;
        }

        productService.deleteAllByCategoryId(category.getId());

        categoryService.findAllByParentId(category.getId())
                .forEach(this::deleteById);

        categoryService.deleteById(category.getId());
    }

}
