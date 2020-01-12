package com.app.service;

import com.app.dto.CartDTO;
import com.app.exceptions.AppException;
import com.app.exceptions.ExceptionCodes;
import com.app.mappers.CartMapper;
import com.app.mappers.ProductMapper;
import com.app.mappers.UserMapper;
import com.app.model.Cart;
import com.app.model.Product;
import com.app.model.User;
import com.app.repository.CartRepository;
import com.app.repository.ProductRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class CartServiceTests { // TODO: 31.12.2019 all tests

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartService cartService;

    @TestConfiguration
    public static class AppTestConfiguration {

        @MockBean
        private CartRepository cartRepository;

        @MockBean
        private UserRepository userRepository;

        @MockBean
        ProductRepository productRepository;

        @Bean
        public CartService cartService() {
            return new CartService(cartRepository, userRepository, productRepository);
        }
    }

    @Test
    @DisplayName("getCart - one cart in DB")
    void test1() { // TODO: 2020-01-11

        Cart cart = Cart.builder().id(1L).build();
        List<Cart> carts = List.of(cart);

        Mockito
                .when(cartRepository.findAll())
                .thenReturn(carts);

        CartDTO expectedCart = cartService.getCart(1L);

        Assertions.assertEquals(expectedCart, CartMapper.toDto(cart));
    }

    /*@Test
    @DisplayName("getUsersActiveCart - user has no cart")
    void test1() {

        User user = User.builder().id(5L).login("User A").build();

        Mockito
                .when(userRepository.findAll())
                .thenReturn(List.of(user));

        Mockito
                .when(cartRepository.findAll())
                .thenReturn(List.of());

        CartDTO expectedCart = CartDTO.builder().userDTO(UserMapper.toDto(user)).build();
        CartDTO actualCart = cartService.getUsersActiveCart(UserMapper.toDto(user));

        Assertions.assertEquals(expectedCart, actualCart);
    }

    @Test
    @DisplayName("getUsersActiveCart - user has got one cart")
    void test2() {
        User user = User.builder().id(4L).login("User A").build();
        Cart cart = Cart.builder().cartClosed(false).build();
        cart.setUser(user);

        Mockito
                .when(userRepository.findAll())
                .thenReturn(List.of(user));

        Mockito
                .when(cartRepository.findAll())
                .thenReturn(List.of(cart));

        CartDTO expectedCart = CartDTO.builder().cartClosed(false).userDTO(UserMapper.toDto(user)).build();
        CartDTO actualCart = cartService.getUsersActiveCart(UserMapper.toDto(user));

        Assertions.assertEquals(expectedCart, actualCart);
    }

    @Test
    @DisplayName("getUsersActiveCart - user has got many carts")
    void test3() {
        User user = User.builder().id(3L).login("User A").build();
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
        CartDTO actualCart = cartService.getUsersActiveCart(UserMapper.toDto(user));

        Assertions.assertEquals(expectedCart, actualCart);
    }

    @Test
    @DisplayName("getAllUsersCarts - user has no carts")
    void test4() {
        User user = User.builder().id(5L).login("User A").build();

        Mockito
                .when(userRepository.findAll())
                .thenReturn(List.of(user));

        Mockito
                .when(cartRepository.findAll())
                .thenReturn(List.of());

        List<CartDTO> actualCartsList = cartService.getAllUsersCarts(UserMapper.toDto(user));

        Assertions.assertEquals(0, actualCartsList.size());
    }

    @Test
    @DisplayName("getAllUsersCarts - user has one cart closed")
    void test5() {

        User user = User.builder().id(6L).build();
        Cart cart = Cart.builder().cartClosed(true).build();
        cart.setUser(user);

        Mockito
                .when(userRepository.findAll())
                .thenReturn(List.of(user));

        Mockito
                .when(cartRepository.findAll())
                .thenReturn(List.of(cart));

        List<CartDTO> expectedCartsList = List.of(CartMapper.toDto(cart));
        List<CartDTO> actualCartsList = cartService.getAllUsersCarts(UserMapper.toDto(user));

        Assertions.assertEquals(1, actualCartsList.size());
        Assertions.assertEquals(expectedCartsList, actualCartsList);
    }

    @Test
    @DisplayName("getAllUsersCarts - user has one cart opened")
    void test6() {

        User user = User.builder().id(6L).build();
        Cart cart = Cart.builder().cartClosed(false).build();
        cart.setUser(user);

        Mockito
                .when(userRepository.findAll())
                .thenReturn(List.of(user));

        Mockito
                .when(cartRepository.findAll())
                .thenReturn(List.of(cart));

        List<CartDTO> expectedCartsList = List.of(CartMapper.toDto(cart));
        List<CartDTO> actualCartsList = cartService.getAllUsersCarts(UserMapper.toDto(user));

        Assertions.assertEquals(1, actualCartsList.size());
        Assertions.assertEquals(expectedCartsList, actualCartsList);
    }

    @Test
    @DisplayName("getAllUsersCarts - user has many carts")
    void test7() {

        User user = User.builder().id(6L).build();
        Cart cart1 = Cart.builder().cartClosed(false).build();
        Cart cart2 = Cart.builder().cartClosed(true).build();
        Cart cart3 = Cart.builder().cartClosed(true).build();
        Cart cart4 = Cart.builder().cartClosed(true).build();
        cart1.setUser(user);
        cart2.setUser(user);
        cart3.setUser(user);
        cart4.setUser(user);

        Mockito
                .when(userRepository.findAll())
                .thenReturn(List.of(user));

        Mockito
                .when(cartRepository.findAll())
                .thenReturn(List.of(cart1, cart2, cart3, cart4));

        List<CartDTO> expectedCartsList = List.of(CartMapper.toDto(cart1),
                CartMapper.toDto(cart2), CartMapper.toDto(cart3), CartMapper.toDto(cart4));
        List<CartDTO> actualCartsList = cartService.getAllUsersCarts(UserMapper.toDto(user));

        Assertions.assertEquals(4, actualCartsList.size());
        Assertions.assertEquals(expectedCartsList, actualCartsList);
    }

    @Test
    @DisplayName("getOneCart")
    void test8() {
        User user = User.builder().id(4L).login("User A").build();
        Cart cart = Cart.builder().id(6L).cartClosed(false).build();
        cart.setUser(user);

        Mockito
                .when(userRepository.findAll())
                .thenReturn(List.of(user));

        Mockito
                .when(cartRepository.findAll())
                .thenReturn(List.of(cart));

        CartDTO expectedCart = CartDTO.builder().id(6L).cartClosed(false).userDTO(UserMapper.toDto(user)).build();
        CartDTO actualCart = cartService.getActiveCart(6L);

        Assertions.assertEquals(expectedCart, actualCart);
    }

    @Test
    @DisplayName("addProductToCart - empty cart")
    void test9() {

        User user = User.builder().id(4L).login("User A").build();
        Product product = Product.builder().id(7L).description("Product 1").quantity(11).build();
        Cart cart = Cart.builder().id(6L).cartClosed(false).build();
        cart.setUser(user);

        Mockito
                .when(userRepository.findAll())
                .thenReturn(List.of(user));

        Mockito
                .when(cartRepository.findAll())
                .thenReturn(List.of(cart));

        Mockito
                .when(productRepository.findAll())
                .thenReturn(List.of(product));

        cartService.addProductToCart(ProductMapper.toDto(product), UserMapper.toDto(user));

        Assertions.assertEquals(1, cartRepository.findAll().size());
    }

    @Test
    @DisplayName("addProductToCart - user has got cart")
    void test10() {

        // TODO: 2019-10-23 dodawanie do nowego koszyka jak poprzedni jest zamknięty

        *//*User user = User.builder().id(4L).login("User A").build();
        Product product = Product.builder().id(7L).description("Product 1").quantity(11).build();
        Cart cart1 = Cart.builder().id(6L).cartClosed(true).build();
        Cart cart2 = Cart.builder().id(7L).cartClosed(false).build();
        cart1.setUser(user);
        cart2.setUser(user);

        Mockito
                .when(userRepository.findAll())
                .thenReturn(List.of(user));

        Mockito
                .when(cartRepository.findAll())
                .thenReturn(List.of(cart1, cart2));

        Mockito
                .when(productRepository.findAll())
                .thenReturn(List.of(product));

        cartService.addProductToCart(ProductMapper.toDto(product), UserMapper.toDto(user));

        Assertions.assertEquals(2, cartRepository.findAll().size());*//*
    }*/
}