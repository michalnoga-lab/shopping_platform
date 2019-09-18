package com.app.service;

import com.app.dto.CompanyDTO;
import com.app.mappers.CompanyMapper;
import com.app.model.Company;
import com.app.model.User;
import com.app.repository.CompanyRepository;
import com.app.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class CompanyServiceTests {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyService companyService;

    @TestConfiguration
    public static class AppTestConfiguration {

        @MockBean
        private CompanyRepository companyRepository;

        @MockBean
        private UserRepository userRepository;

        @Bean
        public CompanyService companyService() {
            return new CompanyService(companyRepository, userRepository);
        }
    }

    @Test
    @DisplayName("getCompanyOfUser")
    void test1() {

        Company company1 = Company.builder()
                .users(new HashSet<>(List.of(
                        User.builder().login("login A").build(),
                        User.builder().login("login B").build(),
                        User.builder().login("login C").build()
                )))
                .build();

        Company company2 = Company.builder()
                .users(new HashSet<>(List.of(
                        User.builder().login("login D").build(),
                        User.builder().login("login E").build()
                )))
                .build();

        Company company3 = Company.builder()
                .users(new HashSet<>(List.of(
                        User.builder().login("login F").build()
                )))
                .build();

        List<Company> companies = List.of(company1, company2, company3);

        Mockito
                .when(companyRepository.findAll()).thenReturn(companies);


        System.out.println("##############################");

        companyRepository
                .findAll()
                .stream()
                .forEach(company -> System.out.println(company));

        CompanyDTO expectedCompany1 = CompanyMapper.toDto(company1);
        CompanyDTO actualCompany1 = companyService.getCompanyOfUser("login A");

        Assertions.assertEquals(expectedCompany1, actualCompany1);
    }
}