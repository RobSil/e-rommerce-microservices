package com.robsil.cartservice.service.facade;

import com.robsil.cartservice.data.domain.Category;
import com.robsil.cartservice.service.CategoryService;
import com.robsil.cartservice.service.ProductService;
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
