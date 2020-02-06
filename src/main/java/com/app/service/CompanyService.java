package com.app.service;

import com.app.dto.CompanyDTO;
import com.app.exceptions.AppException;
import com.app.exceptions.ExceptionCodes;
import com.app.mappers.CompanyMapper;
import com.app.model.Company;
import com.app.model.User;
import com.app.repository.CompanyRepository;
import com.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

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

    public List<CompanyDTO> findAll() {
        List<Company> companies = companyRepository.findAll();

        return companies
                .stream()
                .map(CompanyMapper::toDto)
                .collect(Collectors.toList());
    }

    public CompanyDTO findById(Long companyId) {
        if (companyId == null) {
            throw new AppException(ExceptionCodes.SERVICE_COMPANY, "findById - company ID is null");
        }
        if (companyId <= 0) {
            throw new AppException(ExceptionCodes.SERVICE_COMPANY, "findById - company ID less than zero");
        }
        return companyRepository.findById(companyId)
                .stream()
                .map(CompanyMapper::toDto)
                .findFirst()
                .orElseThrow(() -> new AppException(ExceptionCodes.SERVICE_COMPANY, "findById - no company with ID: " + companyId));
    }

    public CompanyDTO disableEnable(Long companyId, Boolean enabled) {
        if (companyId == null) {
            throw new AppException(ExceptionCodes.SERVICE_COMPANY, "disableEnable - company ID is null");
        }
        if (companyId <= 0) {
            throw new AppException(ExceptionCodes.SERVICE_COMPANY, "disableEnable - company ID less than zero");
        }
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new AppException(ExceptionCodes.SERVICE_COMPANY, "disableEnable - no company with ID: " + companyId));
        company.setActive(enabled);
        companyRepository.save(company);
        return CompanyMapper.toDto(company);
    }

    public CompanyDTO edit(Long companyId) {
        if (companyId == null) {
            throw new AppException(ExceptionCodes.SERVICE_COMPANY, "edit - company ID is null");
        }
        if (companyId <= 0) {
            throw new AppException(ExceptionCodes.SERVICE_COMPANY, "edit - company ID less than zero");
        }

        // TODO: 2020-01-25 edit company method body

        return CompanyDTO.builder().id(1L).build();
    }
}