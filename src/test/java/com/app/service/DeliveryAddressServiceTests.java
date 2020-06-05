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
import java.util.Optional;

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
    @DisplayName("findAll - user has no addresses")
    void test10() {

        DeliveryAddress deliveryAddress1 = DeliveryAddress.builder().street("Address 1").hidden(false).build();
        DeliveryAddress deliveryAddress2 = DeliveryAddress.builder().street("Address 2").hidden(false).build();
        DeliveryAddress deliveryAddress3 = DeliveryAddress.builder().street("Address 3").hidden(false).build();

        User user1 = User.builder().id(1L).build();
        User user2 = User.builder().id(2L).build();

        deliveryAddress1.setUser(user2);
        deliveryAddress2.setUser(user2);
        deliveryAddress3.setUser(user2);

        List<DeliveryAddress> addresses = List.of(deliveryAddress1, deliveryAddress2, deliveryAddress3);

        Mockito
                .when(deliveryAddressRepository.findAll())
                .thenReturn(addresses);

        List<DeliveryAddressDTO> actualResult = deliveryAddressService.findAll(1L);

        Assertions.assertEquals(0, actualResult.size());
    }

    @Test
    @DisplayName("findAll - user has one address")
    void test11() {

        DeliveryAddress deliveryAddress1 = DeliveryAddress.builder().street("Address 1").hidden(false).build();
        DeliveryAddress deliveryAddress2 = DeliveryAddress.builder().street("Address 2").hidden(false).build();
        DeliveryAddress deliveryAddress3 = DeliveryAddress.builder().street("Address 3").hidden(false).build();

        User user1 = User.builder().id(1L).build();
        User user2 = User.builder().id(2L).build();

        deliveryAddress1.setUser(user1);
        deliveryAddress2.setUser(user2);
        deliveryAddress3.setUser(user2);

        List<DeliveryAddress> addresses = List.of(deliveryAddress1, deliveryAddress2, deliveryAddress3);

        Mockito
                .when(deliveryAddressRepository.findAll())
                .thenReturn(addresses);

        List<DeliveryAddressDTO> expectedResult = List.of(DeliveryAddressMapper.toDto(deliveryAddress1));
        List<DeliveryAddressDTO> actualResult = deliveryAddressService.findAll(1L);

        Assertions.assertEquals(1, actualResult.size());
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("findAll - user has many addresses")
    void test12() {

        DeliveryAddress deliveryAddress1 = DeliveryAddress.builder().street("Address 1").hidden(false).build();
        DeliveryAddress deliveryAddress2 = DeliveryAddress.builder().street("Address 2").hidden(false).build();
        DeliveryAddress deliveryAddress3 = DeliveryAddress.builder().street("Address 3").hidden(false).build();

        User user1 = User.builder().id(1L).build();
        User user2 = User.builder().id(2L).build();

        deliveryAddress1.setUser(user1);
        deliveryAddress2.setUser(user1);
        deliveryAddress3.setUser(user2);

        List<DeliveryAddress> addresses = List.of(deliveryAddress1, deliveryAddress2, deliveryAddress3);

        Mockito
                .when(deliveryAddressRepository.findAll())
                .thenReturn(addresses);

        List<DeliveryAddressDTO> expectedResult = List.of(DeliveryAddressMapper.toDto(deliveryAddress1),
                DeliveryAddressMapper.toDto(deliveryAddress2));
        List<DeliveryAddressDTO> actualResult = deliveryAddressService.findAll(1L);

        Assertions.assertEquals(2, actualResult.size());
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("add")
    void test20() {

        User user = User.builder().id(2L).build();
        DeliveryAddressDTO deliveryAddressDTO = DeliveryAddressDTO.builder().id(1L).build();
        Optional<User> userOptional = Optional.of(user);

        Mockito
                .when(userRepository.findById(user.getId()))
                .thenReturn(userOptional);

        DeliveryAddressDTO expectedAddress = DeliveryAddressDTO.builder().id(1L)
                .userDTO(UserMapper.toDto(user))
                .hidden(false)
                .build();

        DeliveryAddressDTO actualAddress = deliveryAddressService.add(deliveryAddressDTO, user.getId());

        Assertions.assertEquals(expectedAddress, actualAddress);
    }

    @Test
    @DisplayName("hide")
    void test30() {

        DeliveryAddress deliveryAddress = DeliveryAddress.builder().id(1L).build();
        DeliveryAddressDTO deliveryAddressDTO = DeliveryAddressDTO.builder().id(1L).build();
        Optional<DeliveryAddress> deliveryAddressOptional = Optional.of(deliveryAddress);

        Mockito
                .when(deliveryAddressRepository.findById(deliveryAddressDTO.getId()))
                .thenReturn(deliveryAddressOptional);

        DeliveryAddressDTO expectedAddress = DeliveryAddressDTO.builder().id(1L)
                .hidden(true)
                .build();

        DeliveryAddressDTO actualAddress = deliveryAddressService.hide(deliveryAddressDTO.getId());

        Assertions.assertEquals(expectedAddress, actualAddress);
    }
}