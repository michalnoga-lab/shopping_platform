package com.app.mappers;

import com.app.dto.CompanyCodeDTO;
import com.app.model.CompanyCode;

public interface CompanyCodeMapper {

    static CompanyCodeDTO toDto(CompanyCode companyCode) {
        return companyCode == null ? null : CompanyCodeDTO.builder()
                .id(companyCode.getId())
                .codeFromOptima(companyCode.getCodeFromOptima())
                .build();
    }

    static CompanyCode fromDto(CompanyCodeDTO companyCodeDTO) {
        return companyCodeDTO == null ? null : CompanyCode.builder()
                .id(companyCodeDTO.getId())
                .codeFromOptima(companyCodeDTO.getCodeFromOptima())
                .build();
    }
}