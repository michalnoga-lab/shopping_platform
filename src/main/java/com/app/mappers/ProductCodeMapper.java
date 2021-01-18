package com.app.mappers;

import com.app.dto.ProductCodeDTO;
import com.app.model.ProductCode;

public interface ProductCodeMapper {

    static ProductCodeDTO toDto(ProductCode productCode) {
        return productCode == null ? null : ProductCodeDTO.builder()
                .id(productCode.getId())
                .codeFromOptima(productCode.getCodeFromOptima())
                .build();
    }

    static ProductCode fromDto(ProductCodeDTO productCodeDTO) {
        return productCodeDTO == null ? null : ProductCode.builder()
                .id(productCodeDTO.getId())
                .codeFromOptima(productCodeDTO.getCodeFromOptima())
                .build();
    }
}