package com.robsil.productservice.service.facade;

import com.robsil.erommerce.jwtintegration.model.UserAuthenticationToken;
import com.robsil.erommerce.protoservice.util.util.IdUtil;
import com.robsil.erommerce.userentityservice.data.domain.Authority;
import com.robsil.model.exception.http.BadRequestException;
import com.robsil.model.exception.http.ForbiddenException;
import com.robsil.model.exception.http.HttpConflictException;
import com.robsil.productservice.data.domain.Product;
import com.robsil.productservice.model.product.ProductCreateRequest;
import com.robsil.productservice.model.product.ProductSaveRequest;
import com.robsil.productservice.service.CategoryService;
import com.robsil.productservice.service.ProductService;
import com.robsil.proto.MerchantServiceGrpc;
import com.robsil.proto.MerchantStoreServiceGrpc;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProductFacadeService {

    private final ProductService productService;
    private final CategoryService categoryService;
    @GrpcClient("merchant-service")
    private MerchantStoreServiceGrpc.MerchantStoreServiceBlockingStub merchantStoreServiceBlockingStub;
    @GrpcClient("merchant-service")
    private MerchantServiceGrpc.MerchantServiceBlockingStub merchantServiceBlockingStub;

    @Transactional
    public Product create(ProductCreateRequest req, UserAuthenticationToken user) {
        var category = categoryService.findById(req.categoryId());
        if (req.merchantStoreId() == null) {
            if (user.isIn(List.of(Authority.ADMIN.toString(), Authority.SUPER_ADMIN.toString()))) {
                return productService.create(req, category);
            } else {
                throw new ForbiddenException("Not enough authorities for creating for default store.");
            }
        }

        if (user.isMerchant()) {
            var merchant = merchantServiceBlockingStub.findByUserId(IdUtil.of(user.getId()));
            var merchantStore = merchantStoreServiceBlockingStub.findByMerchantId(IdUtil.of(merchant.getId()));

            if (!merchantStore.getIsNotBlocked()) {
                throw new HttpConflictException("Merchant store is blocked, can't add new products.");
            }

            return productService.create(req, category);
        }

        throw new BadRequestException("Couldn't find a way to create a product.");
    }

    @Transactional
    public Product save(ProductSaveRequest req) {
        var category = categoryService.findById(req.categoryId());

        return productService.save(req, category);
    }

}
