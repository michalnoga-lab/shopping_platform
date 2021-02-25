package com.app.mappers;

import com.app.dto.CompanyDTO;
import com.app.model.Company;
import com.app.model.Price;

import java.util.HashSet;

public interface CompanyMapper {

    static CompanyDTO toDto(Company company) {
        return company == null ? null : CompanyDTO.builder()
                .id(company.getId())
                .city(company.getCity())
                .name(company.getName())
                .nip(company.getNip())
                .postCode(company.getPostCode())
                .street(company.getStreet())
                .streetNumber(company.getStreetNumber())
                .active(company.getActive())
                .defaultPrice(company.getDefaultPrice())
                .paymentInDays(company.getPaymentInDays())
                .nameShortcut(company.getNameShortcut())
                .build();
    }

    static Company fromDto(CompanyDTO companyDTO) {
        return companyDTO == null ? null : Company.builder()
                .id(companyDTO.getId())
                .city(companyDTO.getCity())
                .name(companyDTO.getName())
                .nip(companyDTO.getNip())
                .postCode(companyDTO.getPostCode())
                .street(companyDTO.getStreet())
                .streetNumber(companyDTO.getStreetNumber())
                .users(new HashSet<>())
                .products(new HashSet<>())
                .active(companyDTO.getActive())
                .defaultPrice(companyDTO.getDefaultPrice())
                .paymentInDays(companyDTO.getPaymentInDays())
                .nameShortcut(companyDTO.getNameShortcut())
                .companyCode(companyDTO.getCompanyCodeDTO() ==
                        null ? null : CompanyCodeMapper.fromDto(companyDTO.getCompanyCodeDTO()))
                .build();
    }
}