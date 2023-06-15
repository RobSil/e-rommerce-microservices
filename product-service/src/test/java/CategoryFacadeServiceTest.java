import com.robsil.productservice.service.CategoryService;
import com.robsil.productservice.service.ProductService;
import com.robsil.productservice.service.facade.CategoryFacadeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CategoryFacadeServiceTest {

    @Mock
    private CategoryService categoryService;
    @Mock
    private ProductService productService;

    @InjectMocks
    private CategoryFacadeService service;

    @Test
    void deleteById() {
        //given

        //when

        //expect
    }
}
