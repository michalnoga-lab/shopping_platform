package com.app.service;

import com.app.dto.UserDTO;
import com.app.mappers.UserMapper;
import com.app.model.Role;
import com.app.model.User;
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
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @TestConfiguration
    public static class AppTestConfiguration {

        @MockBean
        private UserRepository userRepository;

        @Bean
        public UserService userService() {
            return new UserService(userRepository);
        }
    }

    @Test
    @DisplayName("findUserByLogin")
    void test10() {

        User user = User.builder().login("User A").build();

        Mockito
                .when(userRepository.findAll())
                .thenReturn(List.of(user));

        UserDTO expectedUser = UserMapper.toDto(user);
        UserDTO actualUser = userService.findUserByLogin("User A");

        Assertions.assertEquals(expectedUser, actualUser);
    }

    @Test
    @DisplayName("addUser")
    void test20() {

        UserDTO userDTO = UserDTO.builder().id(1L).build();

        UserDTO expectedUser = UserDTO.builder()
                .id(1L)
                .enabled(true)
                .role(Role.USER)
                .build();

        UserDTO actualUser = userService.addUser(userDTO);

        Assertions.assertEquals(expectedUser, actualUser);
    }

    @Test
    @DisplayName("findAll - no users in DB")
    void test30() {

        List<User> users = List.of();
        List<UserDTO> expectedUsers = List.of();

        Mockito
                .when(userRepository.findAll())
                .thenReturn(users);

        List<UserDTO> actualUsers = userService.findAll();

        Assertions.assertEquals(expectedUsers, actualUsers);
    }

    @Test
    @DisplayName("findAll - many users in DB")
    void test31() {

        User user1 = User.builder().id(1L).build();
        User user2 = User.builder().id(2L).build();
        User user3 = User.builder().id(3L).build();

        List<User> users = List.of(user1, user2, user3);

        UserDTO userDTO1 = UserDTO.builder().id(1L).build();
        UserDTO userDTO2 = UserDTO.builder().id(2L).build();
        UserDTO userDTO3 = UserDTO.builder().id(3L).build();

        List<UserDTO> expectedUsers = List.of(userDTO1, userDTO2, userDTO3);

        Mockito
                .when(userRepository.findAll())
                .thenReturn(users);

        List<UserDTO> actualUsers = userService.findAll();

        Assertions.assertEquals(expectedUsers, actualUsers);
    }

    @Test
    @DisplayName("findById")
    void test40() {

        User user = User.builder().id(1L).build();
        Optional<User> userOptional = Optional.of(user);

        Mockito
                .when(userRepository.findById(user.getId()))
                .thenReturn(userOptional);

        UserDTO actualUser = userService.findById(user.getId());
        UserDTO expectedUser = UserDTO.builder().id(1L).build();

        Assertions.assertEquals(expectedUser, actualUser);
    }

    @Test
    @DisplayName("disableEnable - enable when null")
    void test50() {

        User user = User.builder().id(1L).build();
        Optional<User> userOptional = Optional.of(user);

        Mockito
                .when(userRepository.findById(user.getId()))
                .thenReturn(userOptional);

        UserDTO actualUser = userService.disableEnable(user.getId(), true);
        UserDTO expectedUser = UserDTO.builder().id(1L).enabled(true).build();

        Assertions.assertEquals(expectedUser, actualUser);
    }

    @Test
    @DisplayName("disableEnable - disable when null")
    void test51() {

        User user = User.builder().id(1L).build();
        Optional<User> userOptional = Optional.of(user);

        Mockito
                .when(userRepository.findById(user.getId()))
                .thenReturn(userOptional);

        UserDTO actualUser = userService.disableEnable(user.getId(), false);
        UserDTO expectedUser = UserDTO.builder().id(1L).enabled(false).build();

        Assertions.assertEquals(expectedUser, actualUser);
    }

    @Test
    @DisplayName("disableEnable - enable when disabled")
    void test52() {

        User user = User.builder().id(1L).enabled(false).build();
        Optional<User> userOptional = Optional.of(user);

        Mockito
                .when(userRepository.findById(user.getId()))
                .thenReturn(userOptional);

        UserDTO actualUser = userService.disableEnable(user.getId(), true);
        UserDTO expectedUser = UserDTO.builder().id(1L).enabled(true).build();

        Assertions.assertEquals(expectedUser, actualUser);
    }

    @Test
    @DisplayName("disableEnable - disable when enabled")
    void test53() {

        User user = User.builder().id(1L).enabled(true).build();
        Optional<User> userOptional = Optional.of(user);

        Mockito
                .when(userRepository.findById(user.getId()))
                .thenReturn(userOptional);

        UserDTO actualUser = userService.disableEnable(user.getId(), false);
        UserDTO expectedUser = UserDTO.builder().id(1L).enabled(false).build();

        Assertions.assertEquals(expectedUser, actualUser);
    }
}