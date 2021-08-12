package com.app.domain.auction_product;

import java.util.function.Function;

/**
 * AuctionProductUtil interface
 * Provides utilities for AuctionProduct
 *
 * @see AuctionProduct
 */
public interface AuctionProductUtil {
    Function<AuctionProduct, Long> toId = AuctionProduct::getId;
}