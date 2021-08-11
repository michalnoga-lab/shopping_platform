package com.app.domain.order.repository;

import com.app.domain.configs.repository.CrudRepository;
import com.app.domain.order.Order;

/**
 * OrderRepository
 * Provides CRUD methods to use with Order
 *
 * @see CrudRepository
 * @see Order
 */
public interface OrderRepository extends CrudRepository<Order, Long> {
}