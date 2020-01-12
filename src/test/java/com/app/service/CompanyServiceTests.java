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
    @DisplayName("getCompanyOfUser - one user, one company")
    void test1() {

        User user1 = User.builder().id(1L).build();

        Company company1 = Company.builder()
                .users(new HashSet<>(List.of(user1))).build();

        List<Company> companies = List.of(company1);

        Mockito
                .when(companyRepository.findAll()).thenReturn(companies);

        CompanyDTO expectedCompany1 = CompanyMapper.toDto(company1);
        CompanyDTO actualCompany1 = companyService.getCompanyOfUser(1L);

        Assertions.assertEquals(expectedCompany1, actualCompany1);
    }

    @Test
    @DisplayName("getCompanyOfUser - one user, many companies")
    void test2() {

        User user1 = User.builder().id(1L).build();

        Company company1 = Company.builder()
                .users(new HashSet<>(List.of(user1))).build();
        Company company2 = Company.builder()
                .users(new HashSet<>(List.of())).build();
        Company company3 = Company.builder()
                .users(new HashSet<>(List.of())).build();

        List<Company> companies = List.of(company1, company2, company3);

        Mockito
                .when(companyRepository.findAll()).thenReturn(companies);

        CompanyDTO expectedCompany1 = CompanyMapper.toDto(company1);
        CompanyDTO actualCompany1 = companyService.getCompanyOfUser(1L);

        Assertions.assertEquals(expectedCompany1, actualCompany1);
    }

    @Test
    @DisplayName("getCompanyOfUser - many users, many companies")
    void test3() {

        User user1 = User.builder().id(1L).build();
        User user2 = User.builder().id(2L).build();
        User user3 = User.builder().id(3L).build();
        User user4 = User.builder().id(4L).build();
        User user5 = User.builder().id(5L).build();
        User user6 = User.builder().id(6L).build();

        Company company1 = Company.builder()
                .users(new HashSet<>(List.of(user1, user2, user3))).build();

        Company company2 = Company.builder()
                .users(new HashSet<>(List.of(user4, user5))).build();

        Company company3 = Company.builder()
                .users(new HashSet<>(List.of(user6))).build();

        List<Company> companies = List.of(company1, company2, company3);

        Mockito
                .when(companyRepository.findAll()).thenReturn(companies);

        CompanyDTO expectedCompany1 = CompanyMapper.toDto(company1);
        CompanyDTO expectedCompany2 = CompanyMapper.toDto(company2);
        CompanyDTO expectedCompany3 = CompanyMapper.toDto(company3);

        CompanyDTO actualCompany1 = companyService.getCompanyOfUser(1L);
        CompanyDTO actualCompany2 = companyService.getCompanyOfUser(2L);
        CompanyDTO actualCompany3 = companyService.getCompanyOfUser(3L);
        CompanyDTO actualCompany4 = companyService.getCompanyOfUser(4L);
        CompanyDTO actualCompany5 = companyService.getCompanyOfUser(5L);
        CompanyDTO actualCompany6 = companyService.getCompanyOfUser(6L);

        Assertions.assertEquals(expectedCompany1, actualCompany1);
        Assertions.assertEquals(expectedCompany1, actualCompany2);
        Assertions.assertEquals(expectedCompany1, actualCompany3);
        Assertions.assertEquals(expectedCompany2, actualCompany4);
        Assertions.assertEquals(expectedCompany3, actualCompany5);
        Assertions.assertEquals(expectedCompany3, actualCompany6);
    }
}