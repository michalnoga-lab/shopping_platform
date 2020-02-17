package com.app.service;

import com.app.dto.CompanyDTO;
import com.app.dto.ProductDTO;
import com.app.dto.ProductSearchDTO;
import com.app.mappers.CompanyMapper;
import com.app.mappers.ProductMapper;
import com.app.model.Cart;
import com.app.model.Product;
import com.app.repository.CartRepository;
import com.app.repository.CompanyRepository;
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

import java.util.*;
import java.util.stream.Collectors;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ProductService productService;

    @TestConfiguration
    public static class AppTestConfiguration {

        @MockBean
        private ProductRepository productRepository;

        @MockBean
        private CartRepository cartRepository;

        @MockBean
        private CompanyRepository companyRepository;

        @Bean
        public ProductService productService() {
            return new ProductService(productRepository, cartRepository, companyRepository);
        }
    }

    @Test
    @DisplayName("findAll")
    void test10() {

        Product product1 = Product.builder().id(1L).hidden(false).build();
        Product product2 = Product.builder().id(2L).hidden(false).build();
        Product product3 = Product.builder().id(3L).hidden(false).build();
        Product product4 = Product.builder().id(4L).hidden(false).build();
        Product product5 = Product.builder().id(5L).hidden(false).build();

        ProductDTO productDTO1 = ProductDTO.builder().id(1L).hidden(false).build();
        ProductDTO productDTO2 = ProductDTO.builder().id(2L).hidden(false).build();
        ProductDTO productDTO3 = ProductDTO.builder().id(3L).hidden(false).build();
        ProductDTO productDTO4 = ProductDTO.builder().id(4L).hidden(false).build();
        ProductDTO productDTO5 = ProductDTO.builder().id(5L).hidden(false).build();

        List<Product> products = List.of(product1, product2, product3, product4, product5);
        List<ProductDTO> expectedProducts = List.of(productDTO1, productDTO2, productDTO3, productDTO4, productDTO5);

        Mockito
                .when(productRepository.findAll())
                .thenReturn(products);

        List<ProductDTO> actualProducts = productService.findAll()
                .stream()
                .sorted(Comparator.comparing(ProductDTO::getId))
                .collect(Collectors.toList());

        Assertions.assertEquals(expectedProducts, actualProducts);
    }

    @Test
    @DisplayName("getProductsOfCompany")
    void test20() {

        Product product1 = Product.builder().name("Product 1").hidden(false).build();
        Product product2 = Product.builder().name("Product 2").hidden(false).build();
        Product product3 = Product.builder().name("Product 3").hidden(false).build();
        Product product4 = Product.builder().name("Product 4").hidden(false).build();
        Product product5 = Product.builder().name("Product 5").hidden(false).build();

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

        List<ProductDTO> actualProductOfCompany1 = productService.findProductsOfCompany(companyDTO1.getId());
        List<ProductDTO> actualProductOfCompany2 = productService.findProductsOfCompany(companyDTO2.getId());

        List<ProductDTO> expectedProductOfCompany1 = List.of(ProductMapper.toDto(product1),
                ProductMapper.toDto(product2), ProductMapper.toDto(product3));
        List<ProductDTO> expectedProductOfCompany2 = List.of(ProductMapper.toDto(product4));

        Assertions.assertEquals(expectedProductOfCompany1, actualProductOfCompany1);
        Assertions.assertEquals(expectedProductOfCompany2, actualProductOfCompany2);
    }

    @Test
    @DisplayName("getOneProduct")
    void test30() {

        Product product1 = Product.builder().id(1L).name("Product 1").build();
        Product product2 = Product.builder().id(2L).name("Product 2").build();
        Product product3 = Product.builder().id(3L).name("Product 3").build();
        Product product4 = Product.builder().id(4L).name("Product 4").build();
        Product product5 = Product.builder().id(5L).name("Product 5").build();

        List<Product> products = List.of(product1, product2, product3, product4, product5);

        Mockito
                .when(productRepository.findById(1L))
                .thenReturn(Optional.of(product1));

        ProductDTO expectedProduct = productService.getOneProduct(1L);

        Assertions.assertEquals(expectedProduct, ProductMapper.toDto(product1));
    }

    @Test
    @DisplayName("getProductsOfCart")
    void test40() {

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
        Optional<Cart> cartOptional = Optional.of(cart);
        cart.setProducts(new HashSet<>(products));

        Mockito
                .when(cartRepository.findById(cart.getId()))
                .thenReturn(cartOptional);

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
    void test50() {
        Product product1 = Product.builder().id(1L).name("AAA").hidden(false).build();
        Product product2 = Product.builder().id(2L).name("BBB").hidden(false).build();
        Product product3 = Product.builder().id(3L).name("CCC").hidden(false).build();
        Product product4 = Product.builder().id(4L).name("DDD").hidden(false).build();
        Product product5 = Product.builder().id(5L).name("EEE").hidden(false).build();

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
    void test51() {
        Product product1 = Product.builder().id(1L).name("AAA").hidden(false).build();
        Product product2 = Product.builder().id(2L).name("BBB").hidden(false).build();
        Product product3 = Product.builder().id(3L).name("CCC").hidden(false).build();
        Product product4 = Product.builder().id(4L).name("DDD").hidden(false).build();
        Product product5 = Product.builder().id(5L).name("EEE").hidden(false).build();

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
    void test52() {
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