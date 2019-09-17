package com.app.dto;

import com.app.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class DeliveryAddressDTO {

    private Long id;
    private String address;
    private Customer customer;
}