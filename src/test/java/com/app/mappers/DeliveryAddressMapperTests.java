package com.app.mappers;

import com.app.dto.DeliveryAddressDTO;
import com.app.model.User;
import com.app.model.DeliveryAddress;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class DeliveryAddressMapperTests {

    @Test
    @DisplayName("toDto")
    void test1() {

        DeliveryAddress deliveryAddress = DeliveryAddress.builder()
                .id(1L)
                .address("Example address")
                .customer(User.builder().id(2L).build())
                .build();

        DeliveryAddressDTO expectedResult = DeliveryAddressDTO.builder()
                .id(1L)
                .address("Example address")
                .customer(User.builder().id(2L).build())
                .build();
        DeliveryAddressDTO actualResult = DeliveryAddressMapper.toDto(deliveryAddress);

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("fromDto")
    void test2() {

        DeliveryAddressDTO deliveryAddressDTO = DeliveryAddressDTO.builder()
                .id(1L)
                .address("Example address")
                .customer(User.builder().id(2L).build())
                .build();

        DeliveryAddress expectedResult = DeliveryAddress.builder()
                .id(1L)
                .address("Example address")
                .customer(User.builder().id(2L).build())
                .build();

        DeliveryAddress actualResult = DeliveryAddressMapper.fromDto(deliveryAddressDTO);

        Assertions.assertEquals(expectedResult, actualResult);
    }
}