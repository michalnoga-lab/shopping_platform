package com.app.service;

import com.app.dto.CompanyDTO;
import com.app.dto.ProductDTO;
import com.app.exceptions.AppException;
import com.app.exceptions.ExceptionCodes;
import com.app.mappers.CompanyMapper;
import com.app.model.Company;
import com.app.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final CompanyRepository companyRepository;

    public List<ProductDTO> getAllProductsForSpecificUser(String userLogin) {
        if (userLogin == null) {
            throw new AppException(ExceptionCodes.SERVICE, "user login is null");
        }

        CompanyDTO companyDTO = CompanyMapper.toDto(companyRepository.findByUsers(userLogin));

        return null; // TODO: 2019-09-18
    }
}