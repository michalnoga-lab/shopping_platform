package com.app.service;

import com.app.dto.CompanyDTO;
import com.app.dto.ProductDTO;
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

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

  /*  public List<ProductDTO> getAllProductsForSpecificUser(String userLogin) {
        if (userLogin == null) {
            throw new AppException(ExceptionCodes.SERVICE, "getAllProductsForSpecificUser - user login is null");
        }

        CompanyDTO companyDTO = getCompanyOfUser(userLogin);

        return null; // TODO: 2019-09-18
    }*/

    public CompanyDTO getCompanyOfUser(String userLogin) {
        if (userLogin == null || userLogin.length() == 0) {
            throw new AppException(ExceptionCodes.SERVICE, "getCompanyOfUser - user login is null");
        }
        User user = userRepository.findUserByLogin(userLogin);

        Company company = companyRepository
                .findAll()
                .stream()
                .filter(cmp -> cmp.getUsers().contains(user))
                .findFirst()
                .orElseThrow(() -> new AppException(ExceptionCodes.SERVICE, "getCompanyOfUser - no company related with user"));
        return CompanyMapper.toDto(company);
    }
}