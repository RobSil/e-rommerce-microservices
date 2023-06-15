import com.robsil.model.exception.http.EntityNotFoundException;
import com.robsil.model.exception.http.NotImplementedException;
import com.robsil.productservice.data.domain.Category;
import com.robsil.productservice.data.domain.Product;
import com.robsil.productservice.data.repository.ProductRepository;
import com.robsil.productservice.model.product.MeasureUnit;
import com.robsil.productservice.model.product.ProductCreateRequest;
import com.robsil.productservice.model.product.ProductSaveRequest;
import com.robsil.productservice.model.product.ProductStatus;
import com.robsil.productservice.service.ProductService;
import com.robsil.productservice.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    private ProductService productService;

    @BeforeEach
    public void beforeEach() {
        productService = new ProductServiceImpl(productRepository);
    }

    @Test
    void findByIdFound() {
        //given
        Long id = 1L;
        Product given = Product.builder()
                .id(id)
                .name("somename")
                .build();

        when(productRepository.findById(id)).thenReturn(Optional.of(given));

        //when
        Product returned = productService.findById(id);

        //expect
        verify(productRepository).findById(any());
        assertEquals(given, returned, "returned and given doesn't equals");
    }

    @Test
    void findByIdNotFound() {
        //given
        Long id = 1L;

        when(productRepository.findById(id)).thenReturn(Optional.empty());

        //expect
        assertThrows(EntityNotFoundException.class,
                () -> productService.findById(id),
                "should be not found exception, when got empty optional");
        verify(productRepository).findById(any());
    }

    @Test
    void findBySkuFound() {
        //given
        String sku = "sku1";
        Product given = Product.builder()
                .id(1L)
                .name("somename")
                .sku(sku)
                .build();

        when(productRepository.findBySku(sku)).thenReturn(Optional.of(given));

        //when
        Product returned = productService.findBySku(sku);

        //expect
        verify(productRepository).findBySku(any());
        assertEquals(given, returned, "returned and given doesn't equals");
    }

    @Test
    void findBySkuNotFound() {
        //given
        String sku = "sku1";

        when(productRepository.findBySku(sku)).thenReturn(Optional.empty());

        //expect
        assertThrows(EntityNotFoundException.class,
                () -> productService.findBySku(sku),
                "should be not found exception, when got empty optional");
        verify(productRepository).findBySku(any());
    }

    @Test
    @Disabled("not implemented yet")
    void findAllByCategoryId() {
        throw new NotImplementedException();
    }

    @Test
    @Disabled("not implemented yet")
    void findAllByIdIn() {
        throw new NotImplementedException();
    }

    @Test
    void createSuccess() {
        //given
        Category category = Category.builder()
                .id(1L)
                .build();
        var createRequest = new ProductCreateRequest(category.getId(),
                "name1",
                "sku1",
                BigDecimal.TEN,
                BigDecimal.ONE,
                MeasureUnit.PIECE,
                ProductStatus.IN_STOCK,
                true);

        var argProduct = Product.builder()
                .name(createRequest.getName())
                .sku(createRequest.getSku())
                .price(createRequest.getPrice())
                .quantity(createRequest.getQuantity())
                .measureUnit(createRequest.getMeasureUnit())
                .status(createRequest.getStatus())
                .isActive(createRequest.isActive())
                .build();

        var argProductId = Product.builder()
                .id(1L)
                .name(createRequest.getName())
                .sku(createRequest.getSku())
                .price(createRequest.getPrice())
                .quantity(createRequest.getQuantity())
                .measureUnit(createRequest.getMeasureUnit())
                .status(createRequest.getStatus())
                .isActive(createRequest.isActive())
                .build();


        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);

        //when
        when(productRepository.save(argProduct)).thenReturn(argProductId);
        Product product = productService.create(createRequest, category);

        //expect
        verify(productRepository).save(captor.capture());
        var arg = captor.getValue();
        assertEquals(createRequest.getCategoryId(), category.getId());
        assertEquals(createRequest.getName(), arg.getName());
        assertEquals(createRequest.getSku(), arg.getSku());
        assertEquals(createRequest.getPrice(), arg.getPrice());
        assertEquals(createRequest.getQuantity(), arg.getQuantity());
        assertEquals(createRequest.getMeasureUnit(), arg.getMeasureUnit());
        assertEquals(createRequest.getStatus(), arg.getStatus());

        assertEquals(product.getId(), argProductId.getId());
        assertEquals(createRequest.getCategoryId(), product.getId());
        assertEquals(createRequest.getName(), product.getName());
        assertEquals(createRequest.getSku(), product.getSku());
        assertEquals(createRequest.getPrice(), product.getPrice());
        assertEquals(createRequest.getQuantity(), product.getQuantity());
        assertEquals(createRequest.getMeasureUnit(), product.getMeasureUnit());
        assertEquals(createRequest.getStatus(), product.getStatus());
    }

    @Test
    void saveSuccess() {
        //given
        Category category1 = Category.builder()
                .id(1L)
                .build();
        Category category2 = Category.builder()
                .id(2L)
                .build();

        var initialProduct = Product.builder()
                .id(1L)
                .category(category1)
                .name("name1")
                .sku("sku1")
                .price(new BigDecimal(11))
                .quantity(new BigDecimal(2))
                .measureUnit(MeasureUnit.PIECE)
                .status(ProductStatus.OUT_OF_STOCK)
                .isActive(false)
                .build();

        var saveRequest = new ProductSaveRequest(initialProduct.getId(),
                category2.getId(),
                "name2",
                "sku2",
                initialProduct.getPrice(),
                initialProduct.getQuantity(),
                MeasureUnit.PIECE,
                ProductStatus.IN_STOCK,
                true);

        var toSave = Product.builder()
                .id(initialProduct.getId())
                .category(category2)
                .name(saveRequest.getName())
                .sku(saveRequest.getSku())
                .price(saveRequest.getPrice())
                .quantity(saveRequest.getQuantity())
                .measureUnit(saveRequest.getMeasureUnit())
                .status(saveRequest.getStatus())
                .isActive(saveRequest.isActive())
                .build();


        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);

        //when
        when(productRepository.findById(initialProduct.getId())).thenReturn(Optional.of(initialProduct));
        when(productRepository.save(toSave)).thenReturn(toSave);
        Product product = productService.save(saveRequest, category2);

        //expect
        verify(productRepository).save(captor.capture());
        verify(productRepository).findById(initialProduct.getId());
        verifyNoMoreInteractions(productRepository);
        var arg = captor.getValue();
        assertEquals(toSave.getCategory().getId(), product.getCategory().getId());
        assertEquals(toSave.getName(), product.getName());
        assertEquals(toSave.getSku(), product.getSku());
        assertEquals(toSave.getPrice(), product.getPrice());
        assertEquals(toSave.getQuantity(), product.getQuantity());
        assertEquals(toSave.getMeasureUnit(), product.getMeasureUnit());
        assertEquals(toSave.getStatus(), product.getStatus());
    }

    @Test
    void saveNotFound() {
        //given
        Long id = 1L;
        Category category1 = Category.builder()
                .id(1L)
                .build();
        var saveRequest = new ProductSaveRequest(id,
                category1.getId(),
                "name2",
                "sku2",
                BigDecimal.ONE,
                BigDecimal.ONE,
                MeasureUnit.PIECE,
                ProductStatus.IN_STOCK,
                true);
        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);

        //when
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        //expect
        assertThrows(EntityNotFoundException.class,
                () -> productService.save(saveRequest, category1),
                "should be exception when got empty optional");
        verify(productRepository).findById(saveRequest.getId());
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    void changeQuantitySuccess() {
        //given
        BigDecimal newQuantity = new BigDecimal(25);
        Category category = Category.builder()
                .id(1L)
                .build();

        var initialProduct = Product.builder()
                .id(1L)
                .category(category)
                .name("name1")
                .sku("sku1")
                .price(new BigDecimal(11))
                .quantity(new BigDecimal(2))
                .measureUnit(MeasureUnit.PIECE)
                .status(ProductStatus.OUT_OF_STOCK)
                .isActive(false)
                .build();

        var toSave = initialProduct;
        toSave.setQuantity(newQuantity);


        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);

        //when
        when(productRepository.findById(initialProduct.getId())).thenReturn(Optional.of(initialProduct));
        when(productRepository.save(toSave)).thenReturn(toSave);
        Product product = productService.changeQuantity(initialProduct.getId(), newQuantity);

        //expect
        verify(productRepository).findById(initialProduct.getId());
        verify(productRepository).save(captor.capture());
        verifyNoMoreInteractions(productRepository);
        var arg = captor.getValue();
        assertEquals(initialProduct.getCategory().getId(), product.getCategory().getId());
        assertEquals(initialProduct.getName(), product.getName());
        assertEquals(initialProduct.getSku(), product.getSku());
        assertEquals(initialProduct.getPrice(), product.getPrice());
        assertEquals(initialProduct.getQuantity(), newQuantity);
        assertEquals(initialProduct.getMeasureUnit(), product.getMeasureUnit());
        assertEquals(initialProduct.getStatus(), product.getStatus());
    }

    @Test
    void changeQuantityNotFound() {
        //given
        BigDecimal newQuantity = new BigDecimal(25);
        Category category = Category.builder()
                .id(1L)
                .build();

        var initialProduct = Product.builder()
                .id(1L)
                .category(category)
                .name("name1")
                .sku("sku1")
                .price(new BigDecimal(11))
                .quantity(new BigDecimal(2))
                .measureUnit(MeasureUnit.PIECE)
                .status(ProductStatus.OUT_OF_STOCK)
                .isActive(false)
                .build();

        var toSave = initialProduct;
        toSave.setQuantity(newQuantity);



        //when
        when(productRepository.findById(initialProduct.getId())).thenReturn(Optional.empty());
        //expect
        assertThrows(EntityNotFoundException.class,
                () -> productService.changeQuantity(initialProduct.getId(), newQuantity),
                "should be not found exception when got empty optional");
        verify(productRepository).findById(initialProduct.getId());
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    void deleteById() {
        //given
        Long id = 1L;

        //when
        productService.deleteById(id);

        //expect
        verify(productRepository).deleteById(id);
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    void deleteAllByCategoryId() {
        //given
        Long id = 1L;

        //when
        productService.deleteAllByCategoryId(id);

        //expect
        verify(productRepository).deleteAllByCategoryId(id);
        verifyNoMoreInteractions(productRepository);
    }
}
