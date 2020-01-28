package com.app.service;

import com.app.exceptions.AppException;
import com.app.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class CartServiceTests {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private DeliveryAddressRepository deliveryAddressRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CartService cartService;

    @TestConfiguration
    public static class AppTestConfiguration {

        @MockBean
        private CartRepository cartRepository;

        @MockBean
        private UserRepository userRepository;

        @MockBean
        private ProductRepository productRepository;

        @MockBean
        private CompanyRepository companyRepository;

        @MockBean
        private DeliveryAddressRepository deliveryAddressRepository;

        @Bean
        public CartService cartService() {
            return new CartService(cartRepository, userRepository, productRepository, companyRepository, deliveryAddressRepository);
        }
    }

    // TODO: 28.01.2020

    @Test
    @DisplayName("getCart - no cart in DB with searching ID")
    void test11() {

        // TODO: 28.01.2020
    }

    @Test
    @DisplayName("getCart - cart ID is null")
    void test12() {

        AppException appException = Assertions.assertThrows(
                AppException.class, () -> cartService.getCart(null));

        Assertions.assertEquals("getCart - cart ID is null", appException.getDescription());
    }

    /**
     * @Test
     * @DisplayName("test when add with null argument")
     * public void test5() {
     * <p>
     * // GIVEN
     * <p>
     * Person p1 = Person.builder().name("A").age(18).build();
     * Person p2 = Person.builder().name("B").age(17).build();
     * <p>
     * Mockito
     * .doThrow(new IllegalArgumentException("xxx"))
     * .when(personRepository.add(ArgumentMatchers.isNull()));
     * <p>
     * Assertions.assertThrows(IllegalArgumentException.class, () -> personService.add(null));
     * }
     */

    @Test
    @DisplayName("getCart - one cart in DB")
    void test2() {
    }

    @Test
    @DisplayName("getUsersActiveCart - user has no cart")
    void test3() {


    }
}