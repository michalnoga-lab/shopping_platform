package com.app.integration.web;

import com.app.PrimaPlatformaApplication;
import com.app.dto.CartDTO;
import com.app.dto.ProductDTO;
import com.app.mappers.ProductMapper;
import com.app.model.Product;
import com.app.service.CartService;
import com.app.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@ExtendWith(SpringExtension.class)

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = PrimaPlatformaApplication.class
)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.tests.properties")
public class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private CartService cartService;

    @Test
    @DisplayName("all")
    void test1() throws Exception {

        mockMvc
                .perform(MockMvcRequestBuilders.get("/products/all").contentType(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }

    @Test
    @DisplayName("buy/{id}")
    void test2() throws Exception {

        ProductDTO product = ProductDTO.builder().id(1L).build();
        Mockito
                .when(productService.getOneProduct(1L))
                .thenReturn(product);

        mockMvc
                .perform(MockMvcRequestBuilders.get("/products/buy/{id}", 1L)
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }

    @Test
    @DisplayName("buy")
    void test3() throws Exception {

        ProductDTO productDTO = ProductDTO.builder().id(2L).build();
        ProductDTO addedProduct = ProductDTO.builder().id(2L).build();
        CartDTO cartDTO = CartDTO.builder().id(3L).build();

        Mockito
                .when(cartService.addProductToCart(productDTO, 2L))
                .thenReturn(cartDTO);

        // TODO: 2020-01-24 org.springframework.web.util.NestedServletException:
        //  Request processing failed; nested exception is AppException{id=null, exceptionCode=VALIDATION, description='ProductDTO'}
        mockMvc
                .perform(MockMvcRequestBuilders.post("/products/buy")
                ).andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }

    private static String toJson(ProductDTO productDTO) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(productDTO);
    }

    @Test
    @DisplayName("remove/{id}")
    void test4() throws Exception {

        mockMvc
                .perform(MockMvcRequestBuilders.post("/products/remove/{id}", 1L)
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
        // TODO: 2020-01-25 org.thymeleaf.exceptions.TemplateProcessingException:
        //  Exception evaluating SpringEL expression: "cart.cartClosed==false" (template: "carts/one" - line 14, col 10)
    }
}