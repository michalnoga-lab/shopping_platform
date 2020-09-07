package com.app.mappers;

import com.app.dto.ProductsInCartDTO;
import com.app.model.ProductsInCart;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

@ExtendWith(SpringExtension.class)
public class ProductsInCartMapperTests {

    @Test
    @DisplayName("toDto")
    void test10() {

        ProductsInCart productsInCart = ProductsInCart.builder()
                .id(1L)
                .name("name")
                .productId(2L)
                .quantity(10)
                .nettPrice(BigDecimal.valueOf(10))
                .vat(23.00)
                .grossPrice(BigDecimal.valueOf(12.30))
                .hidden(false)
                .build();

        ProductsInCartDTO expectedProduct = ProductsInCartDTO.builder()
                .id(1L)
                .name("name")
                .productId(2L)
                .quantity(10)
                .nettPrice(BigDecimal.valueOf(10))
                .vat(23.00)
                .grossPrice(BigDecimal.valueOf(12.30))
                .hidden(false)
                .build();

        ProductsInCartDTO actualProduct = ProductInCartMapper.toDto(productsInCart);

        Assertions.assertEquals(expectedProduct, actualProduct);
    }

    @Test
    @DisplayName("fromDto")
    void test20() {

        ProductsInCartDTO productsInCartDTO = ProductsInCartDTO.builder()
                .id(1L)
                .name("name")
                .productId(2L)
                .quantity(10)
                .nettPrice(BigDecimal.valueOf(10))
                .vat(23.00)
                .grossPrice(BigDecimal.valueOf(12.30))
                .hidden(false)
                .build();

        ProductsInCart expectedProduct = ProductsInCart.builder()
                .id(1L)
                .name("name")
                .productId(2L)
                .quantity(10)
                .nettPrice(BigDecimal.valueOf(10))
                .vat(23.00)
                .grossPrice(BigDecimal.valueOf(12.30))
                .hidden(false)
                .build();

        ProductsInCart actualProduct = ProductInCartMapper.fromDto(productsInCartDTO);

        Assertions.assertEquals(expectedProduct, actualProduct);
    }
}