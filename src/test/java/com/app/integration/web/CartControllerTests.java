package com.app.integration.web;

import com.app.PrimaPlatformaApplication;
import com.app.dto.CartDTO;
import com.app.mappers.CartMapper;
import com.app.repository.CartRepository;
import com.app.service.CartService;
import org.h2.server.web.WebApp;
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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

@ExtendWith(SpringExtension.class)

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = PrimaPlatformaApplication.class
)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.tests.properties")
public class CartControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private CartRepository cartRepository;

    @MockBean
    private CartService cartService;

    @BeforeEach
    private void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @DisplayName("all")
    void test1() throws Exception {

        mockMvc
                .perform(MockMvcRequestBuilders.get("/carts/all").contentType(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }

    @Test
    @DisplayName("/one/{id}")
    void test2() throws Exception {

        CartDTO cart = CartDTO.builder().id(1L).build();
        Mockito
                .when(cartService.getCart(1L))
                .thenReturn(cart);

        Mockito
                .when(cartRepository.findById(1L))
                .thenReturn(Optional.of(CartMapper.fromDto(cart)));

        mockMvc
                .perform(MockMvcRequestBuilders.get("/carts/one/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }

    @Test
    @DisplayName("one")
    void test3() throws Exception {

        mockMvc
                .perform(MockMvcRequestBuilders.get("/carts/one/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }

    @Test
    @DisplayName("closed")
    void test4() throws Exception {

        mockMvc
                .perform(MockMvcRequestBuilders.get("/carts/closed").contentType(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }
}