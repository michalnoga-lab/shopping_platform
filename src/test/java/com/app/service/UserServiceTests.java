package com.app.service;

import com.app.dto.UserDTO;
import com.app.mappers.UserMapper;
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

import java.util.List;

@ExtendWith(SpringExtension.class)
public class UserServiceTests {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @TestConfiguration
    public static class AppTestConfiguration {

        @MockBean
        private CompanyRepository companyRepository;

        @MockBean
        private UserRepository userRepository;

        @Bean
        public UserService userService() {
            return new UserService(userRepository);
        }
    }

    @Test
    @DisplayName("findUserByLogin")
    void test1() {

        User user = User.builder().login("User A").build();

        Mockito
                .when(userRepository.findAll())
                .thenReturn(List.of(user));

        UserDTO expectedUser = UserMapper.toDto(user);
        UserDTO actualUser = userService.findUserByLogin("User A");

        Assertions.assertEquals(expectedUser, actualUser);
    }
}