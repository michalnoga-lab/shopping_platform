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
                .surname(user.getSurname())
                .password(user.getPassword())
                .passwordConfirmation(user.getPasswordConfirmation())
                .enabled(user.getEnabled())
                .role(user.getRole())
                .build();
    }

    static User fromDto(UserDTO customerDTO) {
        return customerDTO == null ? null : User.builder()
                .id(customerDTO.getId())
                .company(customerDTO.getCompanyDTO() == null ? null : CompanyMapper.fromDto(customerDTO.getCompanyDTO()))
                .login(customerDTO.getLogin())
                .name(customerDTO.getName())
                .surname(customerDTO.getSurname())
                .password(customerDTO.getPassword())
                .passwordConfirmation(customerDTO.getPasswordConfirmation())
                .enabled(customerDTO.getEnabled())
                .role(customerDTO.getRole())
                .carts(new HashSet<>())
                .deliveryAddresses(new HashSet<>())
                .build();
    }
}