package com.app.domain.auction_product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * GetAuctionProductDto
 * Returns AuctionProduct as DTO
 *
 * @see com.app.domain.auction_product.AuctionProduct
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class GetAuctionProductDto {

    /**
     * Product ID
     */
    Long id;

    /**
     * Product name given by purchaser
     */
    String name;

    /**
     * Product description given by purchaser
     */
    String description;

    /**
     * Product index in auction given by purchaser
     */
    String auctionIndex;

    /**
     * Product consecutive number in purchasers products table
     */
    String auctionConsecutiveNumber;

    /**
     * Product nett price from auction
     */
    BigDecimal nettPrice;

    /**
     * Product vat rate from auction
     */
    Integer vat;

    /**
     * Product gross price from auction
     */
    BigDecimal grossPrice;

    /**
     * Product unique EAN code
     */
    String ean;
}