package com.app.service;

import com.app.dto.CompanyDTO;
import com.app.dto.UserDTO;
import com.app.exceptions.AppException;
import com.app.exceptions.ExceptionCodes;
import com.app.mappers.CompanyMapper;
import com.app.model.Company;
import com.app.model.User;
import com.app.repository.CompanyRepository;
import com.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    public CompanyDTO getCompanyOfUser(UserDTO userDTO) {
        if (userDTO == null) {
            throw new AppException(ExceptionCodes.SERVICE_COMPANY, "getCompanyOfUser - user is null");
        }

        Company company = companyRepository
                .findAll()
                .stream()
                .filter(cmp -> {
                    Optional<User> userOptional = cmp
                            .getUsers()
                            .stream()
                            .filter(usr -> usr.getLogin().equals(userDTO.getLogin()))
                            .findFirst();
                    return userOptional.isPresent();
                })
                .findFirst()
                .orElseThrow(() -> new AppException(ExceptionCodes.SERVICE_COMPANY, "getCompanyOfUser - no company related with user: " + userDTO.getLogin()));
        return CompanyMapper.toDto(company);
    }
}