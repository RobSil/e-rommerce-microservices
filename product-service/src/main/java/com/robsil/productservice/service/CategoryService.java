package com.robsil.productservice.service;

import com.robsil.productservice.data.domain.Category;
import com.robsil.productservice.model.category.CategoryCreateRequest;
import com.robsil.productservice.model.category.CategorySaveRequest;

import java.util.List;

public interface CategoryService {

    Category findById(Long id);
    List<Category> findAll();
    List<Category> findAllByParentId(Long parentId);
    List<Category> findAllRoots();
    Category create(CategoryCreateRequest request);
    Category save(CategorySaveRequest request);

    void deleteById(Long categoryId);

}
