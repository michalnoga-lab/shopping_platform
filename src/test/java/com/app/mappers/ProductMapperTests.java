package com.app.mappers;

import com.app.dto.CompanyDTO;
import com.app.dto.ProductCodeDTO;
import com.app.dto.ProductDTO;
import com.app.model.Company;
import com.app.model.Product;
import com.app.model.ProductCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

@ExtendWith(SpringExtension.class)
public class ProductMapperTests {

    @Test
    @DisplayName("toDto")
    void test1() {

        Product product = Product.builder()
                .id(1L)
                .name("Example name")
                .numberInAuction("1a")
                .auctionIndex("1aa")
                .description("Description")
                .quantity(1)
                .nettPrice(BigDecimal.ONE)
                .vat(23.0)
                .grossPrice(BigDecimal.ONE)
                .productCode(ProductCode.builder().build())
                .hidden(false)
                .build();

        ProductDTO expectedProduct = ProductDTO.builder()
                .id(1L)
                .name("Example name")
                .numberInAuction("1a")
                .auctionIndex("1aa")
                .description("Description")
                .quantity(1)
                .nettPrice(BigDecimal.ONE)
                .vat(23.0)
                .grossPrice(BigDecimal.ONE)
                .productCodeDTO(ProductCodeDTO.builder().build())
                .hidden(false)
                .build();

        ProductDTO actualProduct = ProductMapper.toDto(product);

        Assertions.assertEquals(expectedProduct, actualProduct);
    }

    @Test
    @DisplayName("fromDto")
    void test2() {

        ProductDTO productDTO = ProductDTO.builder()
                .id(1L)
                .name("Example name")
                .numberInAuction("1a")
                .auctionIndex("1aa")
                .description("Description")
                .quantity(1)
                .nettPrice(BigDecimal.ONE)
                .vat(23.0)
                .grossPrice(BigDecimal.ONE)
                .productCodeDTO(ProductCodeDTO.builder().build())
                .companyDTO(CompanyDTO.builder().id(3L).build())
                .hidden(false)
                .build();

        Product expectedProduct = Product.builder()
                .id(1L)
                .name("Example name")
                .numberInAuction("1a")
                .auctionIndex("1aa")
                .description("Description")
                .quantity(1)
                .nettPrice(BigDecimal.ONE)
                .vat(23.0)
                .grossPrice(BigDecimal.ONE)
                .productCode(ProductCode.builder().build())
                .company(Company.builder().build())
                .hidden(false)
                .build();

        Product actualProduct = ProductMapper.fromDto(productDTO);

        Assertions.assertEquals(expectedProduct, actualProduct);
    }
}