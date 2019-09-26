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
                .when(userRepository.findAll())
                .thenReturn(List.of(user));

        Mockito
                .when(cartRepository.findAll())
                .thenReturn(List.of());

        CartDTO expectedCart = CartDTO.builder().customer(user).build();
        CartDTO actualCart = cartService.getUsersCart(UserMapper.toDto(user));

        Assertions.assertEquals(expectedCart, actualCart);
    }

    @Test
    @DisplayName("getUsersCart - user has got one cart")
    void test2() {
        User user = User.builder().login("User A").build();
        Cart cart = Cart.builder().cartClosed(false).build();
        cart.setUser(user);

        Mockito
                .when(userRepository.findAll())
                .thenReturn(List.of(user));

        Mockito
                .when(cartRepository.findAll())
                .thenReturn(List.of(cart));

        CartDTO expectedCart = CartDTO.builder().cartClosed(false).customer(user).build();
        CartDTO actualCart = cartService.getUsersCart(UserMapper.toDto(user));

        Assertions.assertEquals(expectedCart, actualCart);
    }

    @Test
    @DisplayName("getUsersCart - user has got many carts")
    void test3() {
        User user = User.builder().login("User A").build();
        Cart cart1 = Cart.builder().cartClosed(true).build();
        Cart cart2 = Cart.builder().cartClosed(false).build();
        Cart cart3 = Cart.builder().cartClosed(true).build();

        cart1.setUser(user);
        cart2.setUser(user);
        cart3.setUser(user);

        Mockito
                .when(userRepository.findAll())
                .thenReturn(List.of(user));

        Mockito
                .when(cartRepository.findAll())
                .thenReturn(List.of(cart1, cart2, cart3));

        CartDTO expectedCart = CartMapper.toDto(cart2);
        CartDTO actualCart = cartService.getUsersCart(UserMapper.toDto(user));

        Assertions.assertEquals(expectedCart, actualCart);
    }
}