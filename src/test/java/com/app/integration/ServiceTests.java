package com.app.integration;

import com.app.PrimaPlatformaApplication;
import com.app.dto.CompanyDTO;
import com.app.dto.ProductDTO;
import com.app.mappers.CompanyMapper;
import com.app.model.User;
import com.app.repository.CompanyRepository;
import com.app.repository.ProductRepository;
import com.app.repository.UserRepository;
import com.app.service.CompanyService;
import com.app.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@ExtendWith(SpringExtension.class)

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = PrimaPlatformaApplication.class
)
@TestPropertySource(locations = "classpath:application.tests.properties")
public class ServiceTests {  // TODO: 2019-09-23 remove class ???

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private CompanyService companyService;

    @TestConfiguration
    public static class AppTestConfiguration {

        @MockBean
        private ProductRepository productRepository;

        @MockBean
        private CompanyRepository companyRepository;

        @MockBean
        private UserRepository userRepository;

        @Bean
        public ProductService productService() {
            return new ProductService(productRepository);
        }

        @Bean
        public CompanyService companyService() {
            return new CompanyService(companyRepository, userRepository);
        }
    }

    @Test
    @DisplayName("get all products for logged in user")
    public void test1() {

        User user = User.builder().login("log").build();

        CompanyDTO companyDTO1 = CompanyDTO.builder().name("Example company 1").build();
        CompanyDTO companyDTO2 = CompanyDTO.builder().name("Example company 2").build();

        ProductDTO productDTO1 = ProductDTO.builder().name("Product 1").build();
        ProductDTO productDTO2 = ProductDTO.builder().name("Product 1").build();
        ProductDTO productDTO3 = ProductDTO.builder().name("Product 1").build();

        productDTO1.setCompany(CompanyMapper.fromDto(companyDTO1));
        productDTO2.setCompany(CompanyMapper.fromDto(companyDTO1));
        productDTO3.setCompany(CompanyMapper.fromDto(companyDTO2));

        List<ProductDTO> actualProducts = productService.getProductsOfCompany(companyDTO1);

        List<ProductDTO> expectedProducts = List.of(productDTO1, productDTO2);

        Assertions.assertEquals(expectedProducts, actualProducts);
    }
}