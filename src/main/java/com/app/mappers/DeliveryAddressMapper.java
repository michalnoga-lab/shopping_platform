package com.app.mappers;

import com.app.dto.DeliveryAddressDTO;
import com.app.model.DeliveryAddress;

public interface DeliveryAddressMapper {

    static DeliveryAddressDTO toDto(DeliveryAddress deliveryAddress) {
        return deliveryAddress == null ? null : DeliveryAddressDTO.builder()
                .id(deliveryAddress.getId())
                .street(deliveryAddress.getStreet())
                .phone(deliveryAddress.getPhone())
                .userDTO(deliveryAddress.getUser() == null ? null : UserMapper.toDto(deliveryAddress.getUser()))
                .hidden(deliveryAddress.getHidden())
                .build();
    }

    static DeliveryAddress fromDto(DeliveryAddressDTO deliveryAddressDTO) {
        return deliveryAddressDTO == null ? null : DeliveryAddress.builder()
                .id(deliveryAddressDTO.getId())
                .street(deliveryAddressDTO.getStreet())
                .phone(deliveryAddressDTO.getPhone())
                .user(deliveryAddressDTO.getUserDTO() == null ? null : UserMapper.fromDto(deliveryAddressDTO.getUserDTO()))
                .hidden(deliveryAddressDTO.getHidden())
                .build();
    }
}