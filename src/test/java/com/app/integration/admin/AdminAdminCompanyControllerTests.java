package com.app.integration.admin;

import com.app.PrimaPlatformaApplication;
import com.app.dto.CompanyDTO;
import com.app.model.Company;
import com.app.service.CompanyService;
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
public class AdminAdminCompanyControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompanyService companyService;

    @Test
    @DisplayName("add - GET")
    void test10() throws Exception {

        mockMvc
                .perform(MockMvcRequestBuilders.get("/admin/companies/add")
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }

    @Test
    @DisplayName("add - POST")
    void test20() throws Exception {

        CompanyDTO inputCompany = CompanyDTO.builder().id(1L).build();
        CompanyDTO companyDTO = CompanyDTO.builder().id(1L).build();

        Mockito
                .when(companyService.add(inputCompany))
                .thenReturn(companyDTO);

        mockMvc
                .perform(MockMvcRequestBuilders.post("/admin/companies/add")
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }

    @Test
    @DisplayName("all")
    void test30() throws Exception {

        mockMvc
                .perform(MockMvcRequestBuilders.get("/admin/companies/all")
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }

    @Test
    @DisplayName(("one/{id}"))
    void test40() throws Exception {

        Company company = Company.builder().id(1L).build();
        CompanyDTO companyDTO = CompanyDTO.builder().id(1L).build();

        Mockito
                .when(companyService.findById(company.getId()))
                .thenReturn(companyDTO);

        mockMvc
                .perform(MockMvcRequestBuilders.post("/admin/companies/one/{id}", 1L)
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }

    @Test
    @DisplayName("edit/{id}")
    void test50() throws Exception {

        Company company = Company.builder().id(1L).build();
        CompanyDTO companyDTO = CompanyDTO.builder().id(1L).build();

        Mockito
                .when(companyService.edit(company.getId()))
                .thenReturn(companyDTO);

        mockMvc
                .perform(MockMvcRequestBuilders.post("/admin/companies/edit/{id}", 1L)
                        .contentType(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    @DisplayName("enable/{id}")
    void test60() throws Exception {

        mockMvc
                .perform(MockMvcRequestBuilders.post("/admin/companies/enable/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    @DisplayName("disable/{id}")
    void test70() throws Exception {

        mockMvc
                .perform(MockMvcRequestBuilders.post("/admin/companies/disable/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }
}