package com.app.domain.cart_product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class GetCartProductDto {

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
}