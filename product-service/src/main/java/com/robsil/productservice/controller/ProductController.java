package com.robsil.productservice.controller;

import com.robsil.productservice.model.product.*;
import com.robsil.productservice.service.ProductService;
import com.robsil.productservice.service.dtomapper.ProductDtoMapper;
import com.robsil.productservice.service.facade.ProductFacadeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductFacadeService productFacadeService;
    private final ProductService productService;
    private final ProductDtoMapper productDtoMapper;

    @GetMapping("/check-sku")
    public ResponseEntity<ProductCheckSkuResponse> checkSku(@RequestParam @NotEmpty String sku) {
        var product = productService.findBySku(sku);

        return new ResponseEntity<>(new ProductCheckSkuResponse(true, productDtoMapper.apply(product)), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDto>> search(@RequestParam String text) {
        var products = productService.search(text).stream().map(productDtoMapper).toList();

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductDto> create(@RequestBody @Valid ProductCreateRequest req) {
        return new ResponseEntity<>(productDtoMapper.apply(productFacadeService.create(req)), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ProductDto> save(@RequestBody @Valid ProductSaveRequest req) {
        return new ResponseEntity<>(productDtoMapper.apply(productFacadeService.save(req)), HttpStatus.OK);
    }

    @PutMapping("/quantity")
    public ResponseEntity<ProductDto> changeQuantity(@RequestBody @Valid ProductQuantityRequest req) {
        return new ResponseEntity<>(productDtoMapper.apply(productService.changeQuantity(req.getProductId(), req.getQuantity())), HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long productId) {
        productService.deleteById(productId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
