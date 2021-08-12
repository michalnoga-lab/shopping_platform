package com.app.domain.cart;

import com.app.domain.cart.dto.GetCartDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
public class CartTests {

    @Test
    void contextLoads() {
    }

    @Test
    @DisplayName("Cart to CartDto conversion")
    void toGetCartDto() {

        var cart = Cart
                .builder()
                .id(1L)
                .userId(2L)
                .nettValue(BigDecimal.ZERO)
                .vatValue(BigDecimal.ZERO)
                .grossValue(BigDecimal.ZERO)
                .build();

        var expectedCart = GetCartDto
                .builder()
                .id(1L)
                .userId(2L)
                .nettValue(BigDecimal.ZERO)
                .vatValue(BigDecimal.ZERO)
                .grossValue(BigDecimal.ZERO)
                .build();

        var cartAfterChange = cart.toGetCartDto();

        assertThat(expectedCart).isEqualTo(cartAfterChange);
    }
}