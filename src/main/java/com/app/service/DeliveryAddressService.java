package com.app.service;

import com.app.dto.DeliveryAddressDTO;
import com.app.dto.UserDTO;
import com.app.exceptions.AppException;
import com.app.exceptions.ExceptionCodes;
import com.app.mappers.DeliveryAddressMapper;
import com.app.repository.DeliveryAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeliveryAddressService {

    private final DeliveryAddressRepository deliveryAddressRepository;

    public List<DeliveryAddressDTO> getAll(UserDTO userDTO) {
        if (userDTO == null) {
            throw new AppException(ExceptionCodes.SERVICE_DELIVERY, "getAll - user is null");
        }

        return deliveryAddressRepository
                .findAll()
                .stream()
                .filter(address -> address.getUser().getId().equals(userDTO.getId()))
                .map(DeliveryAddressMapper::toDto)
                .collect(Collectors.toList());
    }
}