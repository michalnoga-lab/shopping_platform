package com.app.mappers;

import com.app.dto.CartDTO;
import com.app.model.Cart;

import java.util.HashSet;

public interface CartMapper {

    static CartDTO toDto(Cart cart) {
        return cart == null ? null : CartDTO.builder()
                .id(cart.getId())
                .cartClosed(cart.getCartClosed())
                .customer(cart.getUser())
                .totalGrossValue(cart.getTotalGrossValue())
                .totalNetValue(cart.getTotalNetValue())
                .totalVatValue(cart.getTotalVatValue())
                .build();
    }

    static Cart fromDto(CartDTO cartDTO) {
        return cartDTO == null ? null : Cart.builder()
                .id(cartDTO.getId())
                .cartClosed(cartDTO.getCartClosed())
                .user(cartDTO.getCustomer())
                .products(new HashSet<>())
                .totalGrossValue(cartDTO.getTotalGrossValue())
                .totalNetValue(cartDTO.getTotalNetValue())
                .totalVatValue(cartDTO.getTotalVatValue())
                .build();
    }
}