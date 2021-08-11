package com.app.domain.cart.repository;

import com.app.domain.cart.Cart;
import com.app.domain.configs.repository.CrudRepository;

/**
 * CartRepository
 * Provides CRUD methods to use with Cart
 *
 * @see CrudRepository
 * @see Cart
 */
public interface CartRepository extends CrudRepository<Cart, Long> {
}