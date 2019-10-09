package com.app.mappers;

import com.app.dto.DeliveryAddressDTO;
import com.app.model.DeliveryAddress;

public interface DeliveryAddressMapper {

    static DeliveryAddressDTO toDto(DeliveryAddress deliveryAddress) {
        return deliveryAddress == null ? null : DeliveryAddressDTO.builder()
                .id(deliveryAddress.getId())
                .address(deliveryAddress.getAddress())
                .phone(deliveryAddress.getPhone())
                .user(deliveryAddress.getUser())
                .build();
    }

    static DeliveryAddress fromDto(DeliveryAddressDTO deliveryAddressDTO) {
        return deliveryAddressDTO == null ? null : DeliveryAddress.builder()
                .id(deliveryAddressDTO.getId())
                .address(deliveryAddressDTO.getAddress())
                .phone(deliveryAddressDTO.getPhone())
                .user(deliveryAddressDTO.getUser())
                .build();
    }
}