package com.app.mappers;

import com.app.dto.CartDTO;
import com.app.model.Cart;
import com.app.model.User;
import com.app.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class CartMapperTests {

    @Test
    @DisplayName("toDto")
    void test1() {
        Cart cart = Cart.builder()
                .id(1L)
                .totalVatValue(BigDecimal.valueOf(23))
                .totalGrossValue(BigDecimal.valueOf(123))
                .totalNetValue(BigDecimal.valueOf(100))
                .products(new HashSet<>(List.of(Product.builder().build())))
                .user(User.builder().id(2L).build())
                .cartClosed(false)
                .build();

        CartDTO expectedCart = CartDTO.builder()
                .id(1L)
                .totalVatValue(BigDecimal.valueOf(23))
                .totalGrossValue(BigDecimal.valueOf(123))
                .totalNetValue(BigDecimal.valueOf(100))
                .user(User.builder().id(2L).build())
                .cartClosed(false)
                .build();

        CartDTO actualCart = CartMapper.toDto(cart);

        Assertions.assertEquals(expectedCart, actualCart);
    }

    @Test
    @DisplayName("fromDto")
    void test2() {
        CartDTO cartDTO = CartDTO.builder()
                .id(1L)
                .totalVatValue(BigDecimal.valueOf(23))
                .totalGrossValue(BigDecimal.valueOf(123))
                .totalNetValue(BigDecimal.valueOf(100))
                .user(User.builder().id(2L).build())
                .cartClosed(false)
                .build();
        Cart expectedCart = Cart.builder()
                .id(1L)
                .totalVatValue(BigDecimal.valueOf(23))
                .totalGrossValue(BigDecimal.valueOf(123))
                .totalNetValue(BigDecimal.valueOf(100))
                .products(new HashSet<>())
                .user(User.builder().id(2L).build())
                .cartClosed(false)
                .build();

        Cart actualCart = CartMapper.fromDto(cartDTO);

        Assertions.assertEquals(expectedCart, actualCart);
    }
}