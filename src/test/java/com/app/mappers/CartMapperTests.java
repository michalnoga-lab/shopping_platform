package com.app.mappers;

import com.app.dto.CartDTO;
import com.app.dto.DeliveryAddressDTO;
import com.app.dto.ProductDTO;
import com.app.dto.UserDTO;
import com.app.model.Cart;
import com.app.model.DeliveryAddress;
import com.app.model.User;
import com.app.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class CartMapperTests { // TODO: 31.12.2019 all tests

    /*@Test
    @DisplayName("toDto")
    void test1() {
        Cart cart = Cart.builder()
                .id(1L)
                .totalVatValue(BigDecimal.valueOf(23))
                .totalGrossValue(BigDecimal.valueOf(123))
                .totalNetValue(BigDecimal.valueOf(100))
                .user(User.builder().id(2L).build())
                .cartClosed(false)
                .deliveryAddress(DeliveryAddress.builder().street("Address 1").build())
                .purchaseTime(LocalDateTime.of(2019, 10, 22, 0, 0, 0))
                .product(Product.builder().id(10L).build())
                .build();

        CartDTO expectedCart = CartDTO.builder()
                .id(1L)
                .totalVatValue(BigDecimal.valueOf(23))
                .totalGrossValue(BigDecimal.valueOf(123))
                .totalNetValue(BigDecimal.valueOf(100))
                .userDTO(UserDTO.builder().id(2L).build())
                .deliveryAddressDTO(DeliveryAddressDTO.builder().street("Address 1").build())
                .cartClosed(false)
                .purchaseTime(LocalDateTime.of(2019, 10, 22, 0, 0, 0))
                .productDTO(ProductDTO.builder().id(10L).build())
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
                .userDTO(UserDTO.builder().id(2L).build())
                .deliveryAddressDTO(DeliveryAddressDTO.builder().street("Address 1").build())
                .cartClosed(false)
                .purchaseTime(LocalDateTime.of(2019, 10, 22, 0, 0, 0))
                .productDTO(ProductDTO.builder().id(10L).build())
                .build();

        Cart expectedCart = Cart.builder()
                .id(1L)
                .totalVatValue(BigDecimal.valueOf(23))
                .totalGrossValue(BigDecimal.valueOf(123))
                .totalNetValue(BigDecimal.valueOf(100))
                .user(User.builder().id(2L).carts(new HashSet<>()).build())
                .cartClosed(false)
                .deliveryAddress(DeliveryAddress.builder().street("Address 1").build())
                .purchaseTime(LocalDateTime.of(2019, 10, 22, 0, 0, 0))
                .product(Product.builder().id(10L).carts(new HashSet<>()).build())
                .build();

        Cart actualCart = CartMapper.fromDto(cartDTO);

        Assertions.assertEquals(expectedCart, actualCart);
    }*/
}