package com.app.infrastructure.controller;

import com.app.application.service.UsersService;
import com.app.domain.user.dto.CreateUserDto;
import com.app.domain.user.dto.CreateUserResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTests {

    @MockBean
    private UsersService usersService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @Test
    @DisplayName("User registration")
    void register() throws Exception {

        var createUserDto = CreateUserDto
                .builder()
                .name("name")
                .surname("surname")
                .email("email")
                .password("password")
                .build();

        var userResponse = CreateUserResponseDto
                .builder()
                .id(1L)
                .build();

        Mockito.when(usersService.createUser(createUserDto)).thenReturn(userResponse);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/users/register")
                                .content(toJson(createUserDto))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(201))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", Matchers.equalTo(1L)));

        // TODO sprawdzić po AppWebConfig
    }

    private static <T> String toJson(T t) {
        try {
            return new ObjectMapper().writeValueAsString(t);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }
}