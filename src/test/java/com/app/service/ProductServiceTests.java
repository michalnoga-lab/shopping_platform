package com.app.service;

import com.app.dto.CompanyDTO;
import com.app.dto.ProductDTO;
import com.app.mappers.CompanyMapper;
import com.app.mappers.ProductMapper;
import com.app.model.Product;
import com.app.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @TestConfiguration
    public static class AppTestConfiguration {

        @MockBean
        private ProductRepository productRepository;

        @Bean
        public ProductService productService() {
            return new ProductService(productRepository);
        }
    }

    @Test
    @DisplayName("getProductsOfCompany")
    public void test1() {

        Product product1 = Product.builder().name("Product 1").build();
        Product product2 = Product.builder().name("Product 2").build();
        Product product3 = Product.builder().name("Product 3").build();
        Product product4 = Product.builder().name("Product 4").build();
        Product product5 = Product.builder().name("Product 5").build();

        CompanyDTO companyDTO1 = CompanyDTO.builder().name("Example company 1").build();
        CompanyDTO companyDTO2 = CompanyDTO.builder().name("Example company 2").build();
        CompanyDTO companyDTO3 = CompanyDTO.builder().name("Example company 3").build();

        product1.setCompany(CompanyMapper.fromDto(companyDTO1));
        product2.setCompany(CompanyMapper.fromDto(companyDTO1));
        product3.setCompany(CompanyMapper.fromDto(companyDTO1));
        product4.setCompany(CompanyMapper.fromDto(companyDTO2));
        product5.setCompany(CompanyMapper.fromDto(companyDTO3));

        Mockito
                .when(productRepository.findAll())
                .thenReturn(List.of(product1, product2, product3, product4, product5));

        List<ProductDTO> actualProductOfCompany1 = productService.getProductsOfCompany(companyDTO1);
        List<ProductDTO> actualProductOfCompany2 = productService.getProductsOfCompany(companyDTO2);

        List<ProductDTO> expectedProductOfCompany1 = List.of(ProductMapper.toDto(product1),
                ProductMapper.toDto(product2), ProductMapper.toDto(product3));
        List<ProductDTO> expectedProductOfCompany2 = List.of(ProductMapper.toDto(product4));

        Assertions.assertEquals(expectedProductOfCompany1, actualProductOfCompany1);
        Assertions.assertEquals(expectedProductOfCompany2, actualProductOfCompany2);
    }

}