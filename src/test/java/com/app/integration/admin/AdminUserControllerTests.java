package com.app.integration.admin;

import com.app.PrimaPlatformaApplication;
import com.app.dto.UserDTO;
import com.app.model.User;
import com.app.service.UserService;
import com.app.validators.UserValidator;
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
public class AdminUserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("add - GET")
    void test1() throws Exception {

        mockMvc
                .perform(MockMvcRequestBuilders.get("/admin/users/add")
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }

    @Test
    @DisplayName("add - POST")
    void test2() throws Exception {

        UserDTO inputUser = UserDTO.builder().id(1L).build();
        UserDTO userDTO = UserDTO.builder().id(1L).build();

        Mockito
                .when(userService.addUser(inputUser))
                .thenReturn(userDTO);

        mockMvc
                .perform(MockMvcRequestBuilders.post("/admin/users/add")
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
        // TODO: 2020-01-25 org.springframework.web.util.NestedServletException: Request processing failed;
        //  nested exception is AppException{id=null, exceptionCode=VALIDATION, description='UserDto'}
    }

    @Test
    @DisplayName("all")
    void test3() throws Exception {

        mockMvc
                .perform(MockMvcRequestBuilders.get("/admin/users/all")
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }

    @Test
    @DisplayName("one/{id}")
    void test4() throws Exception {

        UserDTO userDTO = UserDTO.builder().id(1L).build();

        Mockito
                .when(userService.findById(userDTO.getId()))
                .thenReturn(userDTO);

        mockMvc
                .perform(MockMvcRequestBuilders.post("/admin/users/one/{id}", 1L)
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
        // TODO: 2020-01-25 org.thymeleaf.exceptions.TemplateProcessingException: Exception evaluating SpringEL expression:
        //  "user.companyDTO.name" (template: "/admin/users/one" - line 32, col 20)
    }

    @Test
    @DisplayName("disable")
    void test5() throws Exception {

        mockMvc
                .perform(MockMvcRequestBuilders.post("/admin/users/disable/{id}", 1L)
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    @DisplayName("enable")
    void test6() throws Exception {

        mockMvc
                .perform(MockMvcRequestBuilders.post("/admin/users/enable/{id}", 1L)
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    // TODO: 2020-01-25 edit test
}