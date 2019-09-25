package com.app.service;

import com.app.dto.CartDTO;
import com.app.mappers.CartMapper;
import com.app.mappers.UserMapper;
import com.app.model.Cart;
import com.app.model.User;
import com.app.repository.CartRepository;
import com.app.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
public class CartServiceTests {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartService cartService;

    @TestConfiguration
    public static class AppTestConfiguration {

        @MockBean
        private CartRepository cartRepository;

        @MockBean
        private UserRepository userRepository;

        @Bean
        public CartService cartService() {
            return new CartService(cartRepository, userRepository);
        }
    }

    @Test
    @DisplayName("getUsersCart - user has no cart")
    void test1() {

        User user = User.builder().login("User A").build();

        Mockito
                .when(cartRepository.findAll())
                .thenReturn(List.of());

        CartDTO expectedCart = CartDTO.builder().customer(user).build();
        CartDTO actualCart = cartService.getUsersCart(UserMapper.toDto(user));

        // TODO: 2019-09-25 tutaj skończyłem

        Assertions.assertEquals(expectedCart, actualCart);
    }
}