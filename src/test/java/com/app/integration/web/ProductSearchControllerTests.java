package com.app.integration.web;

import com.app.PrimaPlatformaApplication;
import com.app.dto.ProductSearchDTO;
import com.app.mappers.ProductMapper;
import com.app.model.Product;
import com.app.repository.ProductRepository;
import com.app.service.ProductService;
import com.app.validators.ProductSearchDtoValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.util.List;

@ExtendWith(SpringExtension.class)

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = PrimaPlatformaApplication.class
)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.tests.properties")
public class ProductSearchControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private ProductService productService;

    @Test
    @DisplayName("products - GET")
    void test1() throws Exception {

        mockMvc
                .perform(MockMvcRequestBuilders.get("/search/products").contentType(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }

    @Test
    @DisplayName("products - POST")
    void test2() throws Exception {

        Product product = Product.builder().id(1L).build();
        ProductSearchDTO productSearchDTO = ProductSearchDTO.builder().build();

        Mockito
                .when(productRepository.findAll())
                .thenReturn(List.of(product));

        Mockito
                .when(productService.search(productSearchDTO))
                .thenReturn(List.of(ProductMapper.toDto(product)));

        mockMvc
                .perform(MockMvcRequestBuilders.post("/search/products")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("userInput", "exampleInput"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }
}