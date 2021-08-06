package com.app.domain.auction_product;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode
@ToString
public class AuctionProduct {

    Long id;
    String name;
    String description;
    String auctionIndex;
    String auctionConsecutiveNumber;
    BigDecimal nettPrice;
    Integer vat;
    BigDecimal grossPrice;
}