package com.app.mappers;

import com.app.dto.CompanyDTO;
import com.app.dto.UserDTO;
import com.app.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;

@ExtendWith(SpringExtension.class)
public class UserMapperTests {

    @Test
    @DisplayName("toDto")
    void test1() {

        User user = User.builder()
                .id(1L)
                .company(Company.builder().id(2L).users(new HashSet<>()).products(new HashSet<>()).build())
                .enabled(true)
                .login("Exmaple login")
                .name("Example name")
                .email("email@domain.com")
                .password("Exaplme password")
                .passwordConfirmation("Example password")
                .role(Role.USER)
                .surname("Example surname")
                .deliveryAddresses(new HashSet<>())
                .carts(new HashSet<>())
                .build();

        UserDTO expectedUser = UserDTO.builder()
                .id(1L)
                .companyDTO(CompanyDTO.builder().id(2L).build())
                .enabled(true)
                .login("Exmaple login")
                .email("email@domain.com")
                .name("Example name")
                .password("Exaplme password")
                .passwordConfirmation("Example password")
                .role(Role.USER)
                .surname("Example surname")
                .build();

        UserDTO actualUser = UserMapper.toDto(user);

        Assertions.assertEquals(expectedUser, actualUser);
    }

    @Test
    @DisplayName("fromDto")
    void test2() {

        UserDTO userDTO = UserDTO.builder()
                .id(1L)
                .companyDTO(CompanyDTO.builder().id(2L).build())
                .enabled(false)
                .login("Exmaple login")
                .name("Example name")
                .email("email@domain.com")
                .password("Exaplme password")
                .passwordConfirmation("Example password")
                .role(Role.ADMIN)
                .surname("Example surname")
                .build();

        User expectedUser = User.builder()
                .id(1L)
                .company(Company.builder().id(2L).users(new HashSet<>()).products(new HashSet<>()).build())
                .enabled(false)
                .login("Exmaple login")
                .name("Example name")
                .email("email@domain.com")
                .password("Exaplme password")
                .passwordConfirmation("Example password")
                .role(Role.ADMIN)
                .surname("Example surname")
                .deliveryAddresses(new HashSet<>())
                .carts(new HashSet<>())
                .build();

        User actualCustomer = UserMapper.fromDto(userDTO);

        Assertions.assertEquals(expectedUser, actualCustomer);
    }
}