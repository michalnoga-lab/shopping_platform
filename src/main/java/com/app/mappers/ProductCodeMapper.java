package com.app.mappers;

import com.app.dto.ProductCodeDTO;
import com.app.model.ProductCode;

public class ProductCodeMapper {

    static ProductCodeDTO toDto(ProductCode productCode) {
        return productCode == null ? null : ProductCodeDTO.builder()
                .codeFromOptima(productCode.getCodeFromOptima())
                .build();
    }

    static ProductCode fromDto(ProductCodeDTO productCodeDTO) {
        return productCodeDTO == null ? null : ProductCode.builder()
                .codeFromOptima(productCodeDTO.getCodeFromOptima())
                .build();
    }
}