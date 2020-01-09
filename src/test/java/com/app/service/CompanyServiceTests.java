package com.app.service;

import com.app.dto.CompanyDTO;
import com.app.mappers.CompanyMapper;
import com.app.mappers.UserMapper;
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

        /*User userA = User.builder().login("login A").build();
        User userB = User.builder().login("login B").build();
        User userC = User.builder().login("login C").build();
        User userD = User.builder().login("login D").build();
        User userE = User.builder().login("login E").build();
        User userF = User.builder().login("login F").build();

        Company company1 = Company.builder()
                .users(new HashSet<>(List.of(userA, userB, userC))).build();

        Company company2 = Company.builder()
                .users(new HashSet<>(List.of(userD, userE))).build();

        Company company3 = Company.builder()
                .users(new HashSet<>(List.of(userF))).build();

        List<Company> companies = List.of(company1, company2, company3);

        Mockito
                .when(companyRepository.findAll()).thenReturn(companies);

        CompanyDTO expectedCompany1 = CompanyMapper.toDto(company1);
        CompanyDTO expectedCompany2 = CompanyMapper.toDto(company2);
        CompanyDTO expectedCompany3 = CompanyMapper.toDto(company3);

        CompanyDTO actualCompany1 = companyService.getCompanyOfUser(UserMapper.toDto(userA));
        CompanyDTO actualCompany2 = companyService.getCompanyOfUser(UserMapper.toDto(userB));
        CompanyDTO actualCompany3 = companyService.getCompanyOfUser(UserMapper.toDto(userC));
        CompanyDTO actualCompany4 = companyService.getCompanyOfUser(UserMapper.toDto(userD));
        CompanyDTO actualCompany5 = companyService.getCompanyOfUser(UserMapper.toDto(userE));
        CompanyDTO actualCompany6 = companyService.getCompanyOfUser(UserMapper.toDto(userF));

        Assertions.assertEquals(expectedCompany1, actualCompany1);
        Assertions.assertEquals(expectedCompany1, actualCompany2);
        Assertions.assertEquals(expectedCompany1, actualCompany3);
        Assertions.assertEquals(expectedCompany2, actualCompany4);
        Assertions.assertEquals(expectedCompany3, actualCompany5);
        Assertions.assertEquals(expectedCompany3, actualCompany6);*/
    } // TODO: 09.01.2020
}