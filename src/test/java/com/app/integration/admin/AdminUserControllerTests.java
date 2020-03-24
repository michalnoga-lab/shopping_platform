package com.app.integration.admin;

import com.app.PrimaPlatformaApplication;
import com.app.dto.CompanyDTO;
import com.app.dto.UserDTO;
import com.app.model.Role;
import com.app.service.UserService;
import com.app.validators.UserDtoValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
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
public class AdminUserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private UserService userService;

    @SpyBean
    private UserDtoValidator userDtoValidator;

    @BeforeEach
    private void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @DisplayName("add - GET")
    void test10() throws Exception {

        mockMvc
                .perform(MockMvcRequestBuilders.get("/admin/users/add")
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }

    @Test
    @DisplayName("add - POST")
    public void test20() throws Exception {

        UserDTO inputUser = UserDTO.builder()
                .email("aa@mm.com")
                .role(Role.USER)
                .password("pass")
                .companyDTO(CompanyDTO.builder().build())
                .login("login")
                .enabled(true)
                .name("name")
                .passwordConfirmation("pass")
                .surname("surname")
                .build();
        UserDTO userDTO = UserDTO.builder().id(1L)
                .email("aa@mm.com")
                .role(Role.USER)
                .password("pass")
                .companyDTO(CompanyDTO.builder().build())
                .login("login")
                .enabled(true)
                .name("name")
                .passwordConfirmation("pass")
                .surname("surname")
                .build();

        Mockito
                .when(userService.addUser(inputUser, Role.USER))
                .thenReturn(userDTO);

        mockMvc
                .perform(
                        MockMvcRequestBuilders.post("/admin/users/add")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("email", "aa@gmail.com")
                                .param("password", "passpass")
                                .param("login", "login")
                                .param("name", "name")
                                .param("passwordConfirmation", "passpass")
                                .param("surname", "surname")
                )
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/admin/users/added"))
                .andReturn();
    }

    @Test
    @DisplayName("all")
    void test30() throws Exception {

        mockMvc
                .perform(MockMvcRequestBuilders.get("/admin/users/all")
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }

    @Test
    @DisplayName("one/{id}")
    void test40() throws Exception {

        UserDTO userDTO = UserDTO.builder().id(1L).build();

        Mockito
                .when(userService.findById(userDTO.getId()))
                .thenReturn(userDTO);
    }

    @Test
    @DisplayName("disable")
    void test50() throws Exception {

        mockMvc
                .perform(MockMvcRequestBuilders.post("/admin/users/disable/{id}", 1L)
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    @DisplayName("enable")
    void test60() throws Exception {

        mockMvc
                .perform(MockMvcRequestBuilders.post("/admin/users/enable/{id}", 1L)
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    @DisplayName("edit/{id}")
    void test70() throws Exception {

        UserDTO userDTO = UserDTO.builder().id(1L).build();

        Mockito
                .when(userService.findById(userDTO.getId()))
                .thenReturn(userDTO);

        mockMvc
                .perform(MockMvcRequestBuilders.post("/admin/users/edit/{id}", userDTO.getId())
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }

    @Test
    @DisplayName("edit")
    void test80() throws Exception {

        mockMvc
                .perform(MockMvcRequestBuilders.post("/admin/users/edit")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }
}