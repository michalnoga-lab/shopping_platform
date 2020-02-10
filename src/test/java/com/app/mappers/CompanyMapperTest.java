package com.app.mappers;

import com.app.dto.CompanyDTO;
import com.app.model.Company;
import com.app.model.Price;
import com.app.model.User;
import com.app.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class CompanyMapperTest {

    @Test
    @DisplayName("toDto")
    void test1() {
        Company company = Company.builder()
                .id(1L)
                .city("Example city")
                .users(new HashSet<>(List.of(User.builder().id(2L).build())))
                .name("Example name")
                .nip("1234567890")
                .postCode("12-345")
                .products(new HashSet<>(List.of(Product.builder().id(3L).build())))
                .street("Example street")
                .streetNumber("12A")
                .active(true)
                .defaultPrice(Price.NET)
                .paymentInDays(10)
                .build();

        CompanyDTO expectedCompany = CompanyDTO.builder()
                .id(1L)
                .city("Example city")
                .name("Example name")
                .nip("1234567890")
                .postCode("12-345")
                .street("Example street")
                .streetNumber("12A")
                .active(true)
                .defaultPrice(Price.NET)
                .paymentInDays(10)
                .build();

        CompanyDTO actualCompany = CompanyMapper.toDto(company);

        Assertions.assertEquals(expectedCompany, actualCompany);
    }

    @Test
    @DisplayName("fromDto")
    void test2() {
        CompanyDTO companyDTO = CompanyDTO.builder()
                .id(1L)
                .city("Example city")
                .name("Example name")
                .nip("1234567890")
                .postCode("12-345")
                .street("Example street")
                .streetNumber("12A")
                .active(false)
                .defaultPrice(Price.NET)
                .paymentInDays(10)
                .build();

        Company expectedCompany = Company.builder()
                .id(1L)
                .city("Example city")
                .name("Example name")
                .nip("1234567890")
                .postCode("12-345")
                .street("Example street")
                .streetNumber("12A")
                .products(new HashSet<>())
                .users(new HashSet<>())
                .active(false)
                .defaultPrice(Price.NET)
                .paymentInDays(10)
                .build();

        Company actualCompany = CompanyMapper.fromDto(companyDTO);

        Assertions.assertEquals(expectedCompany, actualCompany);
    }
}