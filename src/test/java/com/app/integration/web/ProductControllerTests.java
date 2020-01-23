package com.app.integration.web;

import com.app.PrimaPlatformaApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
    void test2() throws Exception { // TODO: 2020-01-15 ID filed

        mockMvc
                .perform(MockMvcRequestBuilders.get("/products/buy"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }

    @Test
    @DisplayName("buy")
    void test3() throws Exception {

        // TODO: 2020-01-15 org.springframework.web.util.NestedServletException:
        //  Request processing failed; nested exception is AppException{id=null, exceptionCode=VALIDATION, description='ProductDTO'}

        mockMvc
                .perform(MockMvcRequestBuilders.post("/products/buy"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }
}

/**
 * @ExtendWith(SpringExtension.class)
 * @WebMvcTest(ProductController.class) public class ControllersTests {
 * @MockBean private ProductService productService;
 * @Autowired private MockMvc mockMvc;
 * @Test void test1() throws Exception {
 * <p>
 * Product p1 = Product.builder().name("A").quantity(1).build();
 * Product p2 = Product.builder().name("B").quantity(2).build();
 * Product p3 = Product.builder().name("C").quantity(3).build();
 * List<Product> products = List.of(p1, p2, p3);
 * <p>
 * Mockito.when(productService.findAll()).thenReturn(products);
 * <p>
 * mockMvc
 * .perform(MockMvcRequestBuilders
 * .get("/products")
 * .param("req1", "value1")
 * .contentType(MediaType.APPLICATION_JSON))
 * .andExpect(MockMvcResultMatchers.status().isOk())
 * .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)))
 * .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.equalTo("A")));
 * <p>
 * }
 * @Test void test2() throws Exception {
 * <p>
 * Product p1 = Product.builder().id(1L).name("A").quantity(1).build();
 * Mockito.when(productService.findOne(1L)).thenReturn(p1);
 * <p>
 * mockMvc
 * .perform(MockMvcRequestBuilders
 * .get("/products/{id}", 1L)
 * .contentType(MediaType.APPLICATION_JSON))
 * .andExpect(MockMvcResultMatchers.status().isOk())
 * .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.equalTo("A")));
 * <p>
 * }
 * @Test void test3() throws Exception {
 * <p>
 * Product p1 = Product.builder().name("A").quantity(1).build();
 * Product p11 = Product.builder().id(1L).name("A").quantity(1).build();
 * Mockito.when(productService.add(p1)).thenReturn(p11);
 * <p>
 * var res = mockMvc
 * .perform(MockMvcRequestBuilders
 * .post("/products")
 * .content(toJson(p1))
 * .contentType(MediaType.APPLICATION_JSON))
 * .andExpect(MockMvcResultMatchers.status().isCreated())
 * .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.equalTo(1)));
 * <p>
 * System.out.println(res.andReturn().getResponse().getStatus());
 * }
 * <p>
 * private static String toJson(Product product) throws JsonProcessingException {
 * return new ObjectMapper().writeValueAsString(product);
 * }
 */