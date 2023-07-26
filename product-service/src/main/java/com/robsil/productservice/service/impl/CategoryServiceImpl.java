package com.robsil.productservice.service.impl;

import com.robsil.productservice.data.domain.Category;
import com.robsil.productservice.data.repository.CategoryRepository;
import com.robsil.productservice.model.category.CategoryCreateRequest;
import com.robsil.productservice.model.category.CategorySaveRequest;
import com.robsil.productservice.service.CategoryService;
import com.robsil.model.exception.http.EntityNotFoundException;
import com.robsil.model.exception.http.HttpConflictException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public Category saveEntity(Category category) {
        recursiveCheckParent(category);

        return categoryRepository.save(category);
    }

    private void recursiveCheckParent(Category category) {
        // we have to verify, that category.id doesn't equal any of parent ids.

        Category parent = category.getParent();
        while (parent != null) {
            if (parent.getId().equals(category.getId())) {
                throw new HttpConflictException("Unable to save, there is category root recursion. CategoryID: %s, ParentID: %s"
                        .formatted(category.getId().toString(), parent.getId().toString()));
            }

            parent = parent.getParent();
        }
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> {
            log.info("findById: category can't be found. ID: %s".formatted(id));
            return new EntityNotFoundException("Category not found");
        });
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> findAllByParentId(Long parentId) {
        return categoryRepository.findAllByParentId(parentId);
    }

    @Override
    public List<Category> findAllRoots() {
        return findAllByParentId(null);
    }

    @Override
    @Transactional
    public Category create(CategoryCreateRequest request) {

        Category parent = null;

        if (request.getParentId() != null && request.getParentId() > 0L) {
            parent = findById(request.getParentId());
        }

        var category = Category.builder()
                .title(request.getTitle())
                .parent(parent)
                .build();

        category = saveEntity(category);

        return category;
    }

    @Override
    @Transactional
    public Category save(CategorySaveRequest request) {

        var category = findById(request.getId());

        Category parent = null;

        if (request.getParentId() != null && request.getParentId() > 0L) {
            parent = findById(request.getParentId());
        }

        category.setParent(parent);
        category.setTitle(request.getTitle());

        category = saveEntity(category);

        return category;
    }

    @Override
    public void deleteById(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
