package com.app.mappers;

import com.app.dto.CustomerDTO;
import com.app.model.Customer;

import java.util.HashSet;

public interface CustomerMapper {

    static CustomerDTO toDto(Customer customer) {
        return customer == null ? null : CustomerDTO.builder()
                .id(customer.getId())
                .company(customer.getCompany())
                .name(customer.getName())
                .surname(customer.getSurname())
                .build();
    }

    static Customer fromDto(CustomerDTO customerDTO) {
        return customerDTO == null ? null : Customer.builder()
                .id(customerDTO.getId())
                .company(customerDTO.getCompany())
                .name(customerDTO.getName())
                .surname(customerDTO.getSurname())
                .deliveryAddresses(new HashSet<>())
                .carts(new HashSet<>())
                .build();
    }
}