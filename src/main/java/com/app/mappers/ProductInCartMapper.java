package com.app.mappers;

import com.app.dto.ProductsInCartDTO;
import com.app.model.ProductsInCart;

public interface ProductInCartMapper {

    static ProductsInCartDTO toDto(ProductsInCart productsInCart) {
        return productsInCart == null ? null : ProductsInCartDTO.builder()
                .id(productsInCart.getId())
                .name(productsInCart.getName())
                .productId(productsInCart.getProductId())
                .quantity(productsInCart.getQuantity())
                .nettPrice(productsInCart.getNettPrice())
                .vat(productsInCart.getVat())
                .grossPrice(productsInCart.getGrossPrice())
                .hidden(productsInCart.getHidden())
                .productCodeDTO(productsInCart.getProductCode() == null ? null :
                        ProductCodeMapper.toDto(productsInCart.getProductCode()))
                .build();
    }

    static ProductsInCart fromDto(ProductsInCartDTO productsInCartDTO) {
        return productsInCartDTO == null ? null : ProductsInCart.builder()
                .id(productsInCartDTO.getId())
                .name(productsInCartDTO.getName())
                .productId(productsInCartDTO.getProductId())
                .quantity(productsInCartDTO.getQuantity())
                .nettPrice(productsInCartDTO.getNettPrice())
                .vat(productsInCartDTO.getVat())
                .grossPrice(productsInCartDTO.getGrossPrice())
                .hidden(productsInCartDTO.getHidden())
                .productCode(productsInCartDTO.getProductCodeDTO() == null ? null :
                        ProductCodeMapper.fromDto(productsInCartDTO.getProductCodeDTO()))
                .build();
    }
}