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

    public CompanyDTO getCompanyOfUser(Long userId) {
        if (userId == null) {
            throw new AppException(ExceptionCodes.SERVICE_COMPANY, "getCompanyOfUser - no user with ID: " + userId);
        }

        Company company = companyRepository
                .findAll()
                .stream()
                .filter(cmp -> {
                    Optional<User> userOptional = cmp
                            .getUsers()
                            .stream()
                            .filter(usr -> usr.getId().equals(userId))
                            .findFirst();
                    return userOptional.isPresent();
                })
                .findFirst()
                .orElseThrow(() -> new AppException(ExceptionCodes.SERVICE_COMPANY, "getCompanyOfUser - no company related with user with ID: " + userId));
        return CompanyMapper.toDto(company);
    }

    public CompanyDTO add(CompanyDTO companyDTO) {
        if (companyDTO == null) {
            throw new AppException(ExceptionCodes.SERVICE_COMPANY, "add - company dto is null");
        }
        Company company = CompanyMapper.fromDto(companyDTO);
        company.setActive(true);
        companyRepository.save(company);
        return CompanyMapper.toDto(company);
    }
}