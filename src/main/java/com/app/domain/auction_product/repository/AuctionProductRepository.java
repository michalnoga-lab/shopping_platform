package com.app.domain.auction_product.repository;

import com.app.domain.auction_product.AuctionProduct;
import com.app.domain.configs.repository.CrudRepository;

/**
 * AuctionProduct repository
 * Provides CRUD methods to use with AuctionProduct
 *
 * @see CrudRepository
 * @see AuctionProduct
 */
public interface AuctionProductRepository extends CrudRepository<AuctionProduct, Long> {
}