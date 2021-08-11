package com.app.domain.cart_product.repository;

import com.app.domain.cart_product.CartProduct;
import com.app.domain.configs.repository.CrudRepository;

/**
 * CartProduct repository
 * Provides CRUD methods to use with CartProduct
 *
 * @see CrudRepository
 * @see CartProduct
 */
public interface CartProductRepository extends CrudRepository<CartProduct, Long> {
}