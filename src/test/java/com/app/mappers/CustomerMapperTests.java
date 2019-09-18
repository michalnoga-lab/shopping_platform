package com.app.mappers;

import com.app.dto.CustomerDTO;
import com.app.model.Cart;
import com.app.model.Company;
import com.app.model.Customer;
import com.app.model.DeliveryAddress;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class CustomerMapperTests {

    @Test
    @DisplayName("toDto")
    void test1() {

        Customer customer = Customer.builder()
                .id(1L)
                .carts(new HashSet<>(List.of(Cart.builder().id(2L).build())))
                .company(Company.builder().id(3L).build())
                .deliveryAddresses(new HashSet<>(List.of(DeliveryAddress.builder().id(4L).build())))
                .name("Example name")
                .surname("Example surname")
                .build();

        CustomerDTO expectedCustomer = CustomerDTO.builder()
                .id(1L)
                .company(Company.builder().id(3L).build())
                .name("Example name")
                .surname("Example surname")
                .build();

        CustomerDTO actualCustomer = CustomerMapper.toDto(customer);

        Assertions.assertEquals(expectedCustomer, actualCustomer);
    }

    @Test
    @DisplayName("fromDto")
    void test2() {

        CustomerDTO customerDTO = CustomerDTO.builder()
                .id(1L)
                .company(Company.builder().id(3L).build())
                .name("Example name")
                .surname("Example surname")
                .build();

        Customer expectedCustomer = Customer.builder()
                .id(1L)
                .carts(new HashSet<>())
                .company(Company.builder().id(3L).build())
                .deliveryAddresses(new HashSet<>())
                .name("Example name")
                .surname("Example surname")
                .build();

        Customer actualCustomer = CustomerMapper.fromDto(customerDTO);

        Assertions.assertEquals(expectedCustomer, actualCustomer);
    }
}