package com.robsil.cartservice.controller;

import com.robsil.cartservice.model.product.*;
import com.robsil.cartservice.service.ProductService;
import com.robsil.cartservice.service.dtoMapper.ProductDtoMapper;
import com.robsil.cartservice.service.facade.ProductFacadeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
