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

    @Test
    @DisplayName("Product value change when quantity changes")
    void withChangedValues() {
        var cartProduct = CartProduct
                .builder()
                .id(1L)
                .originId(2L)
                .name("name")
                .quantity(10)
                .nettPrice(BigDecimal.ONE)
                .vat(23)
                .grossPrice(BigDecimal.valueOf(1.23))
                .nettValue(BigDecimal.ZERO)
                .vatValue(BigDecimal.ZERO)
                .grossValue(BigDecimal.ZERO)
                .ean("1234567890")
                .build();

        var expectedCartProduct1 = CartProduct
                .builder()
                .id(1L)
                .originId(2L)
                .name("name")
                .quantity(0)
                .nettPrice(BigDecimal.ONE)
                .vat(23)
                .grossPrice(BigDecimal.valueOf(1.23))
                .nettValue(BigDecimal.ZERO)
                .vatValue(BigDecimal.ZERO)
                .grossValue(BigDecimal.ZERO)
                .ean("1234567890")
                .build();

        BigDecimal bigDecimalOf50 = BigDecimal.ONE.multiply(BigDecimal.valueOf(50)).multiply(BigDecimal.valueOf(23 / 100));
        var expectedCartProduct2 = CartProduct
                .builder()
                .id(1L)
                .originId(2L)
                .name("name")
                .quantity(50)
                .nettPrice(BigDecimal.ONE)
                .vat(23)
                .grossPrice(BigDecimal.valueOf(1.23))
                .nettValue(BigDecimal.ONE.multiply(BigDecimal.valueOf(50)))
                .vatValue(bigDecimalOf50)
                .grossValue(bigDecimalOf50.add(BigDecimal.ONE.multiply(BigDecimal.valueOf(50))))
                .ean("1234567890")
                .build();

        BigDecimal bigDecimalOf100 = BigDecimal.ONE.multiply(BigDecimal.valueOf(100)).multiply(BigDecimal.valueOf(23 / 100));
        var expectedCartProduct3 = CartProduct
                .builder()
                .id(1L)
                .originId(2L)
                .name("name")
                .quantity(100)
                .nettPrice(BigDecimal.ONE)
                .vat(23)
                .grossPrice(BigDecimal.valueOf(1.23))
                .nettValue(BigDecimal.ONE.multiply(BigDecimal.valueOf(100)))
                .vatValue(bigDecimalOf50)
                .grossValue(bigDecimalOf50.add(BigDecimal.ONE.multiply(BigDecimal.valueOf(100))))
                .ean("1234567890")
                .build();


        var expectedCartProductAfterChange1 = cartProduct.withChangedValues(0);
        var expectedCartProductAfterChange2 = cartProduct.withChangedValues(50);
        var expectedCartProductAfterChange3 = cartProduct.withChangedValues(100);

        assertThat(expectedCartProductAfterChange1).isEqualTo(expectedCartProduct1);
        assertThat(expectedCartProductAfterChange2).isEqualTo(expectedCartProduct2);
        assertThat(expectedCartProductAfterChange3).isEqualTo(expectedCartProduct3);
    }
}