package com.app.mappers;

import com.app.dto.CartDTO;
import com.app.model.Cart;
import com.app.model.Product;

public interface CartMapper {

    static CartDTO toDto(Cart cart) {
        return cart == null ? null : CartDTO.builder()
                .id(cart.getId())
                .cartClosed(cart.getCartClosed())
                .userDTO(cart.getUser() == null ? null : UserMapper.toDto(cart.getUser()))
                .totalGrossValue(cart.getTotalGrossValue())
                .totalNetValue(cart.getTotalNetValue())
                .totalVatValue(cart.getTotalVatValue())
                .deliveryAddressDTO(cart.getDeliveryAddress() == null ?
                        null : DeliveryAddressMapper.toDto(cart.getDeliveryAddress()))
                .purchaseTime(cart.getPurchaseTime())
                .productDTO(cart.getProduct() == null ? null : ProductMapper.toDto(cart.getProduct()))
                .build();
    }

    static Cart fromDto(CartDTO cartDTO) {
        return cartDTO == null ? null : Cart.builder()
                .id(cartDTO.getId())
                .cartClosed(cartDTO.getCartClosed())
                .user(cartDTO.getUserDTO() == null ? null : UserMapper.fromDto(cartDTO.getUserDTO()))
                .totalGrossValue(cartDTO.getTotalGrossValue())
                .totalNetValue(cartDTO.getTotalNetValue())
                .totalVatValue(cartDTO.getTotalVatValue())
                .deliveryAddress(cartDTO.getDeliveryAddressDTO() == null ?
                        null : DeliveryAddressMapper.fromDto(cartDTO.getDeliveryAddressDTO()))
                .product(new Product())
                .purchaseTime(cartDTO.getPurchaseTime())
                .build();
    }
}