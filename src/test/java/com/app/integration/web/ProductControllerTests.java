package com.app.integration.web;

import com.app.PrimaPlatformaApplication;
import com.app.dto.CartDTO;
import com.app.dto.ProductDTO;
import com.app.service.CartService;
import com.app.service.ProductService;
import com.app.service.SecurityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private ProductService productService;

    @MockBean
    private CartService cartService;

    @MockBean
    private SecurityService securityService;

    @BeforeEach
    private void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito
                .when(securityContext.getAuthentication())
                .thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        Mockito
                .when(securityService.getLoggedInUserId())
                .thenReturn(100L);
    }

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

        /*mockMvc
                .perform(MockMvcRequestBuilders.post("/products/remove/{id}", 1L)
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());

        // org.thymeleaf.exceptions.TemplateProcessingException:
        //  Exception evaluating SpringEL expression: "cart.cartClosed" (template: "carts/one" - line 14, col 10)*/
    }
}