package com.app.domain.cart_product;

import lombok.*;

import java.math.BigDecimal;

/**
 * CartProduct class
 * Represents product placed in user's cart
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode
@ToString
public class CartProduct {

    /**
     * Product ID
     */
    Long id;

    /**
     * Origin AuctionProduct ID on which CartProduct is based on
     *
     * @see com.app.domain.auction_product.AuctionProduct
     */
    Long originId;

    /**
     * Product name
     */
    String name;

    /**
     * Product current quantity in cart
     */
    Integer quantity;

    /**
     * Product current nett price
     */
    BigDecimal nettPrice;

    /**
     * Product current VAT rate
     */
    Integer vat;

    /**
     * Product current gross price
     */
    BigDecimal grossPrice;

    /**
     * Product current nett value
     */
    BigDecimal nettValue;

    /**
     * Product current VAT value
     */
    BigDecimal vatValue;

    /**
     * Product current gross value
     */
    BigDecimal grossValue;

    /**
     * Product unique EAN code
     */
    String ean;

    // TODO get cart product dto

    /***
     * Updates CartProduct nett value, VAT value and gross value based on new product quantity
     * @param updatedSingleProductQuantity is a new quantity to save in CartProduct quantity filed
     * @return new CartProduct object with updated values
     */
    public CartProduct withChangedValues(int updatedSingleProductQuantity) {
        return CartProduct
                .builder()
                .id(id)
                .originId(originId)
                .name(name)
                .quantity(updatedSingleProductQuantity)
                .nettPrice(nettPrice)
                .vat(vat)
                .grossPrice(grossPrice)
                .nettValue(nettPrice.multiply(BigDecimal.valueOf(updatedSingleProductQuantity)))
                .vatValue(nettPrice.multiply(BigDecimal.valueOf(updatedSingleProductQuantity)).multiply(BigDecimal.valueOf(vat / 100)))
                .grossValue(nettPrice.multiply(BigDecimal.valueOf(updatedSingleProductQuantity)).multiply(BigDecimal.valueOf(1 + vat / 100)))
                .ean(ean)
                .build();
    }
}