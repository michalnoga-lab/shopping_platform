package com.app.domain.cart_product;

import lombok.*;

import java.math.BigDecimal;

/**
 * Create CartProduct class
 * This class represents product placed in user's cart
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

    /***
     * This method updates CartProduct nett value, VAT value and gross value based on new product quantity
     * @param updatedQuantity is a new quantity to save in CartProduct quantity filed
     * @return new CartProduct object with updated values
     */
    // TODO nieużywana metoda
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
                .ean(ean)
                .build();
    }
}