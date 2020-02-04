package com.app.integration.admin;

import com.app.PrimaPlatformaApplication;
import com.app.dto.GeneralUserInputDTO;
import com.app.service.ProductService;
import com.app.validators.GeneralUserInputDtoValidator;
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
public class AdminProductControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private GeneralUserInputDTO generalUserInputDTO;

    @MockBean
    private GeneralUserInputDtoValidator generalUserInputDtoValidator;

    @Test
    @DisplayName("all")
    void test1() throws Exception {

        mockMvc
                .perform(MockMvcRequestBuilders.get("/admin/products/all").contentType(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }

    @Test
    @DisplayName("addCode/{id}")
    void test2() throws Exception {

        // TODO: 2020-01-25 org.springframework.web.util.NestedServletException:
        //  Request processing failed; nested exception is java.lang.IllegalStateException:
        //  Invalid target for Validator [generalUserInputDtoValidator bean]: GeneralUserInputDTO(userInput=null)

        mockMvc
                .perform(MockMvcRequestBuilders.post("/admin/products/addCode/{id}", 1L)
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }

    @Test
    @DisplayName("removeCode/{id}")
    void test3() throws Exception {

        mockMvc
                .perform(MockMvcRequestBuilders.post("/admin/products/removeCode/{id}", 1L)
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }
}