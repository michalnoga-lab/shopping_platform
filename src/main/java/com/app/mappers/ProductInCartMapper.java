package com.app.mappers;

import com.app.dto.ProductsInCartDTO;
import com.app.model.ProductsInCart;

public interface ProductInCartMapper {

    static ProductsInCartDTO toDto(ProductsInCart productsInCart) {
        return productsInCart == null ? null : ProductsInCartDTO.builder()
                .id(productsInCart.getId())
                .productId(productsInCart.getProductId())
                .quantity(productsInCart.getQuantity())
                .nettPrice(productsInCart.getNettPrice())
                .vat(productsInCart.getVat())
                .grossPrice(productsInCart.getGrossPrice())
                .cart(productsInCart.getCart())
                .build();
    }

    static ProductsInCart fromDto(ProductsInCartDTO productsInCartDTO) {
        return productsInCartDTO == null ? null : ProductsInCart.builder()
                .id(productsInCartDTO.getId())
                .productId(productsInCartDTO.getProductId())
                .quantity(productsInCartDTO.getQuantity())
                .nettPrice(productsInCartDTO.getNettPrice())
                .vat(productsInCartDTO.getVat())
                .grossPrice(productsInCartDTO.getGrossPrice())
                .cart(productsInCartDTO.getCart())
                .build();
    }
}