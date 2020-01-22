package com.app.service;

import com.app.dto.DeliveryAddressDTO;
import com.app.dto.UserDTO;
import com.app.exceptions.AppException;
import com.app.exceptions.ExceptionCodes;
import com.app.mappers.DeliveryAddressMapper;
import com.app.mappers.UserMapper;
import com.app.model.DeliveryAddress;
import com.app.model.User;
import com.app.repository.DeliveryAddressRepository;
import com.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeliveryAddressService {

    private final DeliveryAddressRepository deliveryAddressRepository;
    private final UserRepository userRepository;

    public List<DeliveryAddressDTO> findAll(Long userId) {
        if (userId == null) {
            throw new AppException(ExceptionCodes.SERVICE_DELIVERY, "findAll - no user with ID: " + userId);
        }
        if (userId <= 0) {
            throw new AppException(ExceptionCodes.SERVICE_DELIVERY, "findAll - ID less than zero: " + userId);
        }
        return deliveryAddressRepository
                .findAll()
                .stream()
                .filter(address -> address.getHidden().equals(false))
                .filter(address -> address.getUser().getId().equals(userId))
                .map(DeliveryAddressMapper::toDto)
                .collect(Collectors.toList());
    }

    public DeliveryAddressDTO add(DeliveryAddressDTO deliveryAddressDTO, Long userId) {
        if (deliveryAddressDTO == null) {
            throw new AppException(ExceptionCodes.SERVICE_DELIVERY, "add - delivery address is null");
        }
        if (userId == null) {
            throw new AppException(ExceptionCodes.SERVICE_DELIVERY, "add - user ID is null: ");
        }
        if (userId <= 0) {
            throw new AppException(ExceptionCodes.SERVICE_DELIVERY, "add - user ID less than zero: " + userId);
        }
        DeliveryAddress deliveryAddress = DeliveryAddressMapper.fromDto(deliveryAddressDTO);
        User user = userRepository.findAll()
                .stream()
                .filter(u -> u.getId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new AppException(ExceptionCodes.SERVICE_DELIVERY, "add - no user with ID: " + userId));

        deliveryAddress.setUser(user);
        deliveryAddress.setHidden(false);
        deliveryAddressRepository.save(deliveryAddress);
        return DeliveryAddressMapper.toDto(deliveryAddress);
    }

    public void hide(Long addressId) {
        if (addressId == null) {
            throw new AppException(ExceptionCodes.SERVICE_DELIVERY, "hide - ID is null");
        }
        if (addressId <= 0) {
            throw new AppException(ExceptionCodes.SERVICE_DELIVERY, "hide - id less than zero");
        }
        DeliveryAddress deliveryAddress = deliveryAddressRepository.findById(addressId)
                .orElseThrow(() -> new AppException(ExceptionCodes.SERVICE_DELIVERY, "hide - no address with ID: " + addressId));
        deliveryAddress.setHidden(true);
        deliveryAddressRepository.save(deliveryAddress);
    }
}