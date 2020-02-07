package com.app.integration.web;

import com.app.PrimaPlatformaApplication;
import com.app.dto.CartDTO;
import com.app.dto.ProductDTO;
import com.app.service.CartService;
import com.app.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
    @DisplayName("buy - POST")
    void test3() throws Exception {

        ProductDTO productDTO = ProductDTO.builder().id(2L).build();
        CartDTO cartDTO = CartDTO.builder().id(3L).build();

        Mockito
                .when(cartService.addProductToCart(productDTO, 2L))
                .thenReturn(cartDTO);

        mockMvc
                .perform(MockMvcRequestBuilders.post("/products/buy")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("quantity", "111"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    @DisplayName("remove/{id}")
    void test4() throws Exception {

        mockMvc
                .perform(MockMvcRequestBuilders.post("/products/remove/{id}", 1L)
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());

        // TODO: 2020-02-06 org.thymeleaf.exceptions.TemplateProcessingException:
        //  Exception evaluating SpringEL expression: "cart.cartClosed" (template: "carts/one" - line 14, col 10)

    }
}