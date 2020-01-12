package com.app.service;

import com.app.dto.CompanyDTO;
import com.app.dto.ProductDTO;
import com.app.dto.ProductSearchDTO;
import com.app.mappers.CompanyMapper;
import com.app.mappers.ProductMapper;
import com.app.model.Cart;
import com.app.model.Product;
import com.app.repository.CartRepository;
import com.app.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;
import java.util.stream.Collectors;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductService productService;

    @TestConfiguration
    public static class AppTestConfiguration {

        @MockBean
        private ProductRepository productRepository;

        @MockBean
        private CartRepository cartRepository;

        @Bean
        public ProductService productService() {
            return new ProductService(productRepository, cartRepository);
        }
    }

    @Test
    @DisplayName("getProductsOfCompany")
    void test1() {

        Product product1 = Product.builder().name("Product 1").build();
        Product product2 = Product.builder().name("Product 2").build();
        Product product3 = Product.builder().name("Product 3").build();
        Product product4 = Product.builder().name("Product 4").build();
        Product product5 = Product.builder().name("Product 5").build();

        CompanyDTO companyDTO1 = CompanyDTO.builder().name("Example company 1").id(1L).build();
        CompanyDTO companyDTO2 = CompanyDTO.builder().name("Example company 2").id(2L).build();
        CompanyDTO companyDTO3 = CompanyDTO.builder().name("Example company 3").id(3L).build();

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

    @Test
    @DisplayName("getOneProduct")
    void test2() { // TODO: 2020-01-11
        //AppException{id=null, exceptionCode=SERVICE_PRODUCT, description='getOneProduct - no product with ID: 1'}

        Product product1 = Product.builder().id(1L).name("Product 1").build();
        Product product2 = Product.builder().id(2L).name("Product 2").build();
        Product product3 = Product.builder().id(3L).name("Product 3").build();
        Product product4 = Product.builder().id(4L).name("Product 4").build();
        Product product5 = Product.builder().id(5L).name("Product 5").build();

        List<Product> products = List.of(product1, product2, product3, product4, product5);

        Mockito
                .when(productRepository.findAll())
                .thenReturn(products);

        ProductDTO expectedProduct = productService.getOneProduct(1L);

        Assertions.assertEquals(expectedProduct, ProductMapper.toDto(product1));
    }

    @Test
    @DisplayName("getProductsOfCart")
    void test3() { // TODO: 2020-01-11

        Product product1 = Product.builder().id(1L).name("Product 1").build();
        Product product2 = Product.builder().id(2L).name("Product 2").build();
        Product product3 = Product.builder().id(3L).name("Product 3").build();
        Product product4 = Product.builder().id(4L).name("Product 4").build();
        Product product5 = Product.builder().id(5L).name("Product 5").build();

        List<Product> products = List.of(product1, product2, product3, product4, product5);

        Mockito
                .when(productRepository.findAll())
                .thenReturn(products);

        Cart cart = Cart.builder().id(6L).build();
        cart.setProducts(new HashSet<>(products));

        Mockito
                .when(cartRepository.findAll())
                .thenReturn(List.of(cart));

        List<ProductDTO> expectedProducts = products
                .stream()
                .map(ProductMapper::toDto)
                .sorted(Comparator.comparing(ProductDTO::getName))
                .collect(Collectors.toList());

        List<ProductDTO> actualProducts = productService.getProductsOfCart(6L)
                .stream()
                .sorted(Comparator.comparing(ProductDTO::getName))
                .collect(Collectors.toList());

        Assertions.assertEquals(expectedProducts, actualProducts);
    }

    @Test
    @DisplayName("search - one product matches")
    void test4() {
        Product product1 = Product.builder().id(1L).name("AAA").build();
        Product product2 = Product.builder().id(2L).name("BBB").build();
        Product product3 = Product.builder().id(3L).name("CCC").build();
        Product product4 = Product.builder().id(4L).name("DDD").build();
        Product product5 = Product.builder().id(5L).name("EEE").build();

        List<Product> products = List.of(product1, product2, product3, product4, product5);

        Mockito
                .when(productRepository.findAll())
                .thenReturn(products);

        List<ProductDTO> expectedProducts = products
                .stream()
                .filter(product -> product.getName().contains("AA"))
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());

        List<ProductDTO> actualProducts =
                productService.search(ProductSearchDTO.builder().userInput("AA").build());

        Assertions.assertEquals(expectedProducts, actualProducts);
    }

    @Test
    @DisplayName("search - two products match")
    void test5() {
        Product product1 = Product.builder().id(1L).name("AAA").build();
        Product product2 = Product.builder().id(2L).name("BBB").build();
        Product product3 = Product.builder().id(3L).name("CCC").build();
        Product product4 = Product.builder().id(4L).name("DDD").build();
        Product product5 = Product.builder().id(5L).name("EEE").build();

        List<Product> products = List.of(product1, product2, product3, product4, product5);

        Mockito
                .when(productRepository.findAll())
                .thenReturn(products);

        List<ProductDTO> expectedProducts = products
                .stream()
                .filter(product -> product.getName().contains("AA") || product.getName().contains("BBB"))
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());

        List<ProductDTO> actualProducts = new ArrayList<>();
        actualProducts.addAll(productService.search(ProductSearchDTO.builder().userInput("AA").build()));
        actualProducts.addAll(productService.search(ProductSearchDTO.builder().userInput("BBB").build()));

        Assertions.assertEquals(expectedProducts, actualProducts);
    }

    @Test
    @DisplayName("search - none product matches")
    void test6() {
        Product product1 = Product.builder().id(1L).name("AAA").build();
        Product product2 = Product.builder().id(2L).name("BBB").build();
        Product product3 = Product.builder().id(3L).name("CCC").build();
        Product product4 = Product.builder().id(4L).name("DDD").build();
        Product product5 = Product.builder().id(5L).name("EEE").build();

        List<Product> products = List.of(product1, product2, product3, product4, product5);

        Mockito
                .when(productRepository.findAll())
                .thenReturn(products);

        List<ProductDTO> expectedProducts = products
                .stream()
                .filter(product -> product.getName().contains("XX"))
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());

        List<ProductDTO> actualProducts =
                productService.search(ProductSearchDTO.builder().userInput("XX").build());

        Assertions.assertEquals(expectedProducts, actualProducts);
    }
}