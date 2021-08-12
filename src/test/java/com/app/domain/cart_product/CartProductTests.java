package com.app.domain.cart_product;

import com.app.domain.cart_product.dto.GetCartProductDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
public class CartProductTests {

    @Test
    void contextLoads() {
    }

    @Test
    @DisplayName("CartProduct to CartProductDto conversion")
    void toGeCartProductDto() {

        var cartProduct = CartProduct
                .builder()
                .id(1L)
                .originId(2L)
                .name("name")
                .quantity(10)
                .nettPrice(BigDecimal.ZERO)
                .vat(23)
                .grossPrice(BigDecimal.ZERO)
                .nettValue(BigDecimal.ZERO)
                .vatValue(BigDecimal.ZERO)
                .grossValue(BigDecimal.ZERO)
                .ean("1234567890")
                .build();

        var expectedCartProduct = GetCartProductDto
                .builder()
                .id(1L)
                .originId(2L)
                .name("name")
                .quantity(10)
                .nettPrice(BigDecimal.ZERO)
                .vat(23)
                .grossPrice(BigDecimal.ZERO)
                .nettValue(BigDecimal.ZERO)
                .vatValue(BigDecimal.ZERO)
                .grossValue(BigDecimal.ZERO)
                .ean("1234567890")
                .build();

        var cartProductAfterChange = cartProduct.toGetCartProductDto();

        assertThat(expectedCartProduct).isEqualTo(cartProductAfterChange);
    }
}