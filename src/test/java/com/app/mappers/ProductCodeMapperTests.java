package com.app.mappers;

import com.app.dto.ProductCodeDTO;
import com.app.model.ProductCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class ProductCodeMapperTests {

    @Test
    @DisplayName("toDto")
    void test10() {

        ProductCode productCode = ProductCode
                .builder()
                .codeFromOptima("op1")
                .build();

        ProductCodeDTO expectedCode = ProductCodeDTO
                .builder()
                .codeFromOptima("op1")
                .build();

        ProductCodeDTO actualCode = ProductCodeMapper.toDto(productCode);

        Assertions.assertEquals(expectedCode, actualCode);
    }

    @Test
    @DisplayName("fromDto")
    void test20() {

        ProductCodeDTO productCodeDTO = ProductCodeDTO.builder()
                .codeFromOptima("op1")
                .build();

        ProductCode expectedProduct = ProductCode.builder()
                .codeFromOptima("op1")
                .build();

        ProductCode actualCode = ProductCodeMapper.fromDto(productCodeDTO);

        Assertions.assertEquals(expectedProduct, actualCode);
    }
}
