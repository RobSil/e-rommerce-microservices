package com.robsil.productservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.robsil.productservice.data.domain.Category;
import com.robsil.productservice.data.domain.Product;
import com.robsil.productservice.data.repository.ProductRepository;
import com.robsil.productservice.model.product.ProductCreateRequest;
import com.robsil.productservice.model.product.ProductSaveRequest;
import com.robsil.productservice.service.ProductService;
import com.robsil.productservice.util.StringUtil;
import com.robsil.model.exception.http.EntityNotFoundException;
import com.robsil.model.exception.http.HttpConflictException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper;

    @SuppressWarnings("java:S116")
    private final String ELASTICSEARCH_URL;
    private static final String PRODUCT_NOT_FOUND_MESSAGE = "Product not found";

    public ProductServiceImpl(ProductRepository productRepository,
                              @Value("${spring.elasticsearch.uri}") List<String> esUris,
                              ObjectMapper objectMapper) {
        this.productRepository = productRepository;
        this.ELASTICSEARCH_URL = esUris.get(0);
        this.objectMapper = objectMapper;
    }

    @Transactional
    public Product saveEntity(Product product) {
        if (StringUtil.isEmpty(product.getSku())) {
            int i = 0;
            DataIntegrityViolationException lastException = null;

            while (true) {
                if (i >= 10) {
                    if (lastException != null) {
                        throw lastException;
                    } else {
                        throw new HttpConflictException("Couldn't save product.");
                    }
                }
                i++;

                try {
                    product.setSku(StringUtil.minimize(product.getName() + "-" + StringUtil.randomAlphanumeric(15)));
                    return productRepository.save(product);
                } catch (DataIntegrityViolationException e) {
                    lastException = e;
                    log.info("saveEntity: got DataIntegrityViolationException. ProductName: %s".formatted(product.getName()));
                    log.info("saveEntity: exception message: %s".formatted(e.getMessage()));
                }
            }
        }

        return productRepository.save(product);
    }

    @Override
    public Product findById(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> {
            log.info("findById: can't find product by ID: %s".formatted(productId));
            return new EntityNotFoundException();
        });
    }

    @Override
    public Product findBySku(String sku) {
        return productRepository.findBySku(sku).orElseThrow(() -> {
            log.info("findById: can't find product by SKU: %s".formatted(sku));
            return new EntityNotFoundException(PRODUCT_NOT_FOUND_MESSAGE);
        });
    }

    @Override
    public Page<Product> findAllByCategoryId(Long categoryId, Pageable pageable) {
        return productRepository.findAllByCategoryId(categoryId, pageable);
    }

    @Override
    public List<Product> findAllByIdIn(List<Long> ids) {
        return productRepository.findAllByIdIn(ids);
    }

    @Override
    public List<Product> search(String searchText) {
        try {
            Map<String, Object> body = new HashMap<>();
            body.put("_source", List.of("id", "sku", "name"));
            body.put("query", Map.of("match", Map.of("name", searchText)));

            var request = HttpRequest.newBuilder(URI.create(ELASTICSEARCH_URL + "/products/_search"))
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(body)))
                    .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                    .build();

            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            //noinspection unchecked
            var hits = (List<Object>) ((Map<String, Object>) objectMapper.readValue(response.body(), Map.class).get("hits")).get("hits");

            //noinspection unchecked
            return hits.stream()
                    .map(obj -> (Map<String, Object>) obj)
                    .map(map -> (Map<String, Object>) map.get("_source"))
                    .map(source -> Product.builder()
                            .id(Long.valueOf((Integer) source.get("id")))
                            .sku((String) source.get("sku"))
                            .name((String) source.get("name"))
                            .build())
                    .map(Product.class::cast)
                    .toList();
        } catch (Exception e) {
            log.info("search: exception occurred! message: " + e.getMessage());
            throw new HttpConflictException(e.getMessage(), e);
        }

    }

    @Override
    @Transactional
    public Product create(ProductCreateRequest req, Category category) {
        var product = Product.builder()
                .category(category)
                .name(req.name())
                .sku(req.sku())
                .price(req.price())
                .quantity(req.quantity())
                .measureUnit(req.measureUnit())
                .status(req.status())
                .isActive(req.isActive())
                .merchantStoreId(req.merchantStoreId())
                .build();

        product = saveEntity(product);

        return product;
    }

    @Override
    @Transactional
    public Product save(ProductSaveRequest req, Category category) {
        var product = findById(req.id());

        if (product == null) {
            log.info("save: can't find product by ID: %s".formatted(req.id()));
            throw new EntityNotFoundException(PRODUCT_NOT_FOUND_MESSAGE);
        }

        product.setCategory(category);
        product.setName(req.name());
        product.setSku(req.sku());
        product.setPrice(req.price());
        product.setQuantity(req.quantity());
        product.setMeasureUnit(req.measureUnit());
        product.setStatus(req.status());
        product.setActive(req.isActive());

        product = saveEntity(product);

        return product;
    }

    @Override
    @Transactional
    public Product changeQuantity(Long productId, BigDecimal quantity) {
        var product = findById(productId);

        product.setQuantity(quantity);

        product = saveEntity(product);

        return product;
    }

    @Override
    public void deleteById(Long productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public void deleteAllByCategoryId(Long categoryId) {
        productRepository.deleteAllByCategoryId(categoryId);
    }
}
