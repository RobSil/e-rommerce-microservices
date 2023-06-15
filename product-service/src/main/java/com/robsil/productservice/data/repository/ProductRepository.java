package com.robsil.productservice.data.repository;

import com.robsil.productservice.data.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findBySku(String sku);
    List<Product> findAllByCategoryId(Long categoryId);
    Page<Product> findAllByCategoryId(Long categoryId, Pageable pageable);
    List<Product> findAllByIdIn(List<Long> ids);

    @Modifying
    @Query("delete from Product product where product.category.id = :categoryId")
    void deleteAllByCategoryId(Long categoryId);

}
