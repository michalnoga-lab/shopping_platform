package com.app.mappers;

import com.app.dto.CartDTO;
import com.app.model.Cart;

import java.util.HashSet;

public interface CartMapper {

    static CartDTO toDto(Cart cart) {
        return cart == null ? null : CartDTO.builder()
                .id(cart.getId())
                .cartClosed(cart.getCartClosed())
                .user(cart.getUser())
                .totalGrossValue(cart.getTotalGrossValue())
                .totalNetValue(cart.getTotalNetValue())
                .totalVatValue(cart.getTotalVatValue())
                .deliveryAddressDTO(cart.getDeliveryAddress() == null ?
                        null : DeliveryAddressMapper.toDto(cart.getDeliveryAddress()))
                .build();
    }

    static Cart fromDto(CartDTO cartDTO) {
        return cartDTO == null ? null : Cart.builder()
                .id(cartDTO.getId())
                .cartClosed(cartDTO.getCartClosed())
                .user(cartDTO.getUser())
                .products(new HashSet<>())
                .totalGrossValue(cartDTO.getTotalGrossValue())
                .totalNetValue(cartDTO.getTotalNetValue())
                .totalVatValue(cartDTO.getTotalVatValue())
                .deliveryAddress(cartDTO.getDeliveryAddressDTO() == null ?
                        null : DeliveryAddressMapper.fromDto(cartDTO.getDeliveryAddressDTO()))
                .build();
    }
}