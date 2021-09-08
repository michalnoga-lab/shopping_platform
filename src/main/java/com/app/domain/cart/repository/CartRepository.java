package com.app.domain.cart.repository;

import com.app.domain.cart.Cart;
import com.app.domain.configs.repository.CrudRepository;

import java.util.List;

/**
 * CartRepository
 * Provides CRUD methods to use with Cart
 *
 * @see CrudRepository
 * @see Cart
 */
public interface CartRepository extends CrudRepository<Cart, Long> {

    List<Cart> findAllForUser(String userEmail);
}