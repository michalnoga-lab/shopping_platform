package com.app.integration.admin;

import com.app.PrimaPlatformaApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
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
public class FileUploadControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    private void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @DisplayName("add - GET")
    void test1() throws Exception {

        mockMvc
                .perform(MockMvcRequestBuilders.get("/admin/products/add")
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }

    @Test
    @DisplayName("get - POST")
    void test2() throws Exception {

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "original", null, (byte[]) null);

        mockMvc
                .perform(MockMvcRequestBuilders.multipart("/admin/products/add", "mockMultipartFile"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                // TODO: 2020-01-25  Error message = Required request part 'file' is not present
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }
}