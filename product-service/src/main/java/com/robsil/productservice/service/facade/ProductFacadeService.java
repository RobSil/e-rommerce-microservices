package com.robsil.productservice.service.facade;

import com.robsil.productservice.data.domain.Product;
import com.robsil.productservice.model.product.ProductCreateRequest;
import com.robsil.productservice.model.product.ProductSaveRequest;
import com.robsil.productservice.service.CategoryService;
import com.robsil.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProductFacadeService {

    private final ProductService productService;
    private final CategoryService categoryService;

    @Transactional
    public Product create(ProductCreateRequest req) {
        var category = categoryService.findById(req.getCategoryId());

        return productService.create(req, category);
    }

    @Transactional
    public Product save(ProductSaveRequest req) {
        var category = categoryService.findById(req.getCategoryId());

        return productService.save(req, category);
    }

}
