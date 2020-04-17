package com.app.integration.restAdmin;

import com.app.PrimaPlatformaApplication;
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
public class AdminUserRestControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private SecurityService securityService;

    @BeforeEach
    private void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);

        Mockito
                .when(securityService.getLoggedInUserId())
                .thenReturn(1L);

        Mockito
                .when(securityContext.getAuthentication())
                .thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);
    }

    @Test()
    @DisplayName("/api/admin/users/add - POST")
    void test10() throws Exception {

        mockMvc
                .perform(MockMvcRequestBuilders.post("/api/admin/users/add").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("/api/admin/users/all - GET")
    void test20() throws Exception {

        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/admin/users/add").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("/api/admin/users/all/{id} - GET")
    void test30() throws Exception {

        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/admin/users/all/{id}", 2L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("/api/admin/users/one/{id} - GET")
    void test40() throws Exception {

        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/admin/users/one/{id}", 2L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("/api/admin/users/disable/{id} - POST")
    void test50() throws Exception {

        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/admin/users/disable/{id}", 2L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("/api/admin/users/enable/{id} - POST")
    void test60() throws Exception {

        mockMvc
                .perform(MockMvcRequestBuilders.post("/api/admin/users/enable/{id}", 2L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("/api/admin/users/disable/{id} - POST")
    void test70() throws Exception {

        mockMvc
                .perform(MockMvcRequestBuilders.post("/api/admin/users/disable/{id}", 2L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("/api/admin/users/edit")
    void test80() throws Exception {

        mockMvc
                .perform(MockMvcRequestBuilders.post("/api/admin/users/edit").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}