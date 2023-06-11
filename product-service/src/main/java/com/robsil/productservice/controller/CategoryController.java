package com.robsil.productservice.controller;

import com.robsil.productservice.model.category.CategoryCreateRequest;
import com.robsil.productservice.model.category.CategoryDto;
import com.robsil.productservice.model.category.CategorySaveRequest;
import com.robsil.productservice.model.product.ProductDto;
import com.robsil.productservice.service.CategoryService;
import com.robsil.productservice.service.ProductService;
import com.robsil.productservice.service.dtomapper.CategoryDtoMapper;
import com.robsil.productservice.service.dtomapper.ProductDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final ProductService productService;
    private final CategoryDtoMapper categoryDtoMapper;
    private final ProductDtoMapper productDtoMapper;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAll() {
        return new ResponseEntity<>(categoryService.findAll().stream().map(categoryDtoMapper).toList(), HttpStatus.OK);
    }

    // TODO: 31.01.2023 make pageable
    @GetMapping("/roots")
    public ResponseEntity<List<CategoryDto>> getAllRoots() {
        return new ResponseEntity<>(categoryService.findAllRoots().stream().map(categoryDtoMapper).toList(), HttpStatus.OK);
    }

    @GetMapping("/{categoryId}/products")
    public ResponseEntity<Page<ProductDto>> getAllProductsByCategoryId(@PathVariable Long categoryId) {
        var rawResult = productService.findAllByCategoryId(categoryId, PageRequest.of(0, 10));
        var contentResult = rawResult.getContent().stream().map(productDtoMapper).toList();

        return new ResponseEntity<>(new PageImpl<>(contentResult, rawResult.getPageable(), rawResult.getTotalElements()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CategoryDto> create(@RequestBody @Valid CategoryCreateRequest req) {
        return new ResponseEntity<>(categoryDtoMapper.apply(categoryService.create(req)), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<CategoryDto> save(@RequestBody @Valid CategorySaveRequest req) {
        return new ResponseEntity<>(categoryDtoMapper.apply(categoryService.save(req)), HttpStatus.OK);
    }

}
