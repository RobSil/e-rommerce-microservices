package com.robsil.cartservice.service;

import com.robsil.cartservice.data.domain.Category;
import com.robsil.cartservice.data.domain.Product;
import com.robsil.cartservice.model.product.ProductCreateRequest;
import com.robsil.cartservice.model.product.ProductSaveRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface ProductService {

    Product findById(Long productId);
    Product findBySku(String sku);
    Page<Product> findAllByCategoryId(Long categoryId, Pageable pageable);
    Product create(ProductCreateRequest req, Category category);
    Product save(ProductSaveRequest req, Category category);

    // consider more about changing this method
    Product changeQuantity(Long productId, BigDecimal quantity);

    void deleteById(Long productId);
    void deleteAllByCategoryId(Long categoryId);

}
