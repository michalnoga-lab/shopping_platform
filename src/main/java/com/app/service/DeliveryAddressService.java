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

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeliveryAddressService {

    private final DeliveryAddressRepository deliveryAddressRepository;
    private final UserRepository userRepository;

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

    public DeliveryAddressDTO add(DeliveryAddressDTO deliveryAddressDTO, UserDTO userDTO) {
        if (deliveryAddressDTO == null) {
            throw new AppException(ExceptionCodes.SERVICE_DELIVERY, "add - delivery address is null");
        }
        if (userDTO == null) {
            throw new AppException(ExceptionCodes.SERVICE_DELIVERY, "add - user is null");
        }

        /*DeliveryAddress deliveryAddress = deliveryAddressRepository.findAll()
                .stream()
                .filter(address -> address.getId().equals(deliveryAddressDTO.getId()))
                .findFirst()
                .orElseThrow(() -> new AppException(ExceptionCodes.SERVICE_DELIVERY, "add - no delivery address with ID:" + deliveryAddressDTO.getId()));*/
        DeliveryAddress deliveryAddress = DeliveryAddressMapper.fromDto(deliveryAddressDTO);
        User user = userRepository.findAll()
                .stream()
                .filter(u -> u.getId().equals(userDTO.getId()))
                .findFirst()
                .orElseThrow(() -> new AppException(ExceptionCodes.SERVICE_DELIVERY, "add - no user with ID: " + userDTO.getId()));

        deliveryAddress.setUser(user);
        deliveryAddressRepository.save(deliveryAddress);
        return DeliveryAddressMapper.toDto(deliveryAddress);
    }
}