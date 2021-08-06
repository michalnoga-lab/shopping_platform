package com.app.domain.cart_product;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode
@ToString
public class CartProduct {

    Long id;
    Long originId;
    String name;
    Integer quantity;
    BigDecimal nettPrice;
    Integer vat;
    BigDecimal grossPrice;
    BigDecimal nettValue;
    BigDecimal vatValue;
    BigDecimal grossValue;

    public CartProduct withChangedValues(int updatedQuantity) {
        return CartProduct
                .builder()
                .id(id)
                .originId(originId)
                .name(name)
                .quantity(updatedQuantity)
                .nettPrice(nettPrice)
                .vat(vat)
                .grossPrice(grossPrice)
                .nettValue(nettPrice.multiply(BigDecimal.valueOf(updatedQuantity)))
                .vatValue(nettPrice.multiply(BigDecimal.valueOf(updatedQuantity)).multiply(BigDecimal.valueOf(vat / 100)))
                .grossValue(nettPrice.multiply(BigDecimal.valueOf(updatedQuantity)).multiply(BigDecimal.valueOf(1 + vat / 100)))
                .build();
    }
}