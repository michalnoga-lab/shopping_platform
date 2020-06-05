package com.app.mappers;

import com.app.dto.UserDTO;
import com.app.model.User;

import java.util.HashSet;

public interface UserMapper {

    static UserDTO toDto(User user) {
        return user == null ? null : UserDTO.builder()
                .id(user.getId())
                .companyDTO(user.getCompany() == null ? null : CompanyMapper.toDto(user.getCompany()))
                .login(user.getLogin())
                .name(user.getName())
                .email(user.getEmail())
                .surname(user.getSurname())
                .password(user.getPassword())
                .passwordConfirmation(user.getPasswordConfirmation())
                .enabled(user.getEnabled())
                .role(user.getRole())
                .build();
    }

    static User fromDto(UserDTO userDTO) {
        return userDTO == null ? null : User.builder()
                .id(userDTO.getId())
                .company(userDTO.getCompanyDTO() == null ? null : CompanyMapper.fromDto(userDTO.getCompanyDTO()))
                .login(userDTO.getLogin())
                .name(userDTO.getName())
                .surname(userDTO.getSurname())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .passwordConfirmation(userDTO.getPasswordConfirmation())
                .enabled(userDTO.getEnabled())
                .role(userDTO.getRole())
                .carts(new HashSet<>())
                .deliveryAddresses(new HashSet<>())
                .build();
    }
}