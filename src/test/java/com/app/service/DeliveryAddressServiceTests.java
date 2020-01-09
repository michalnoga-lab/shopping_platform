package com.app.service;

import com.app.dto.DeliveryAddressDTO;
import com.app.mappers.DeliveryAddressMapper;
import com.app.mappers.UserMapper;
import com.app.model.DeliveryAddress;
import com.app.model.User;
import com.app.repository.DeliveryAddressRepository;
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
public class DeliveryAddressServiceTests {

    @Autowired
    private DeliveryAddressRepository deliveryAddressRepository;

    @Autowired
    private DeliveryAddressService deliveryAddressService;

    @Autowired
    private UserRepository userRepository;

    @TestConfiguration
    public static class TestConfigurationApp {

        @MockBean
        private DeliveryAddressRepository deliveryAddressRepository;

        @MockBean
        private UserRepository userRepository;

        @Bean
        public DeliveryAddressService deliveryAddressService() {
            return new DeliveryAddressService(deliveryAddressRepository, userRepository);
        }
    }

    @Test
    @DisplayName("getAll - user has no addresses")
    void test1() {

       /* DeliveryAddress deliveryAddress1 = DeliveryAddress.builder().street("Address 1").build();
        DeliveryAddress deliveryAddress2 = DeliveryAddress.builder().street("Address 2").build();
        DeliveryAddress deliveryAddress3 = DeliveryAddress.builder().street("Address 3").build();

        User user1 = User.builder().id(5L).build();
        User user2 = User.builder().id(6L).build();

        deliveryAddress1.setUser(user2);
        deliveryAddress2.setUser(user2);
        deliveryAddress3.setUser(user2);

        List<DeliveryAddress> addresses = List.of(deliveryAddress1, deliveryAddress2, deliveryAddress3);

        Mockito
                .when(deliveryAddressRepository.findAll())
                .thenReturn(addresses);

        List<DeliveryAddressDTO> actualResult = deliveryAddressService.getAll(UserMapper.toDto(user1));

        Assertions.assertEquals(0, actualResult.size());*/ // TODO: 09.01.2020  
    }

    @Test
    @DisplayName("getAll - user has one address")
    void test2() {

        /*DeliveryAddress deliveryAddress1 = DeliveryAddress.builder().street("Address 1").build();
        DeliveryAddress deliveryAddress2 = DeliveryAddress.builder().street("Address 2").build();
        DeliveryAddress deliveryAddress3 = DeliveryAddress.builder().street("Address 3").build();

        User user1 = User.builder().id(5L).build();
        User user2 = User.builder().id(6L).build();

        deliveryAddress1.setUser(user1);
        deliveryAddress2.setUser(user2);
        deliveryAddress3.setUser(user2);

        List<DeliveryAddress> addresses = List.of(deliveryAddress1, deliveryAddress2, deliveryAddress3);

        Mockito
                .when(deliveryAddressRepository.findAll())
                .thenReturn(addresses);

        List<DeliveryAddressDTO> expectedResult = List.of(DeliveryAddressMapper.toDto(deliveryAddress1));
        List<DeliveryAddressDTO> actualResult = deliveryAddressService.getAll(UserMapper.toDto(user1));

        Assertions.assertEquals(1, actualResult.size());
        Assertions.assertEquals(expectedResult, actualResult);*/ // TODO: 09.01.2020  
    }

    @Test
    @DisplayName("getAll - user has many addresses")
    void test3() {

        /*DeliveryAddress deliveryAddress1 = DeliveryAddress.builder().street("Address 1").build();
        DeliveryAddress deliveryAddress2 = DeliveryAddress.builder().street("Address 2").build();
        DeliveryAddress deliveryAddress3 = DeliveryAddress.builder().street("Address 3").build();

        User user1 = User.builder().id(5L).build();
        User user2 = User.builder().id(6L).build();

        deliveryAddress1.setUser(user1);
        deliveryAddress2.setUser(user1);
        deliveryAddress3.setUser(user2);

        List<DeliveryAddress> addresses = List.of(deliveryAddress1, deliveryAddress2, deliveryAddress3);

        Mockito
                .when(deliveryAddressRepository.findAll())
                .thenReturn(addresses);

        List<DeliveryAddressDTO> expectedResult = List.of(DeliveryAddressMapper.toDto(deliveryAddress1),
                DeliveryAddressMapper.toDto(deliveryAddress2));
        List<DeliveryAddressDTO> actualResult = deliveryAddressService.getAll(UserMapper.toDto(user1));

        Assertions.assertEquals(2, actualResult.size());
        Assertions.assertEquals(expectedResult, actualResult);*/ // TODO: 09.01.2020
    }
}