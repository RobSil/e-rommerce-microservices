package com.robsil.cartservice.service;

import com.robsil.cartservice.data.domain.Category;
import com.robsil.cartservice.model.category.CategoryCreateRequest;
import com.robsil.cartservice.model.category.CategorySaveRequest;

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
