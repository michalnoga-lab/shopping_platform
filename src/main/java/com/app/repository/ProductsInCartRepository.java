package com.app.repository;

import com.app.model.ProductsInCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsInCartRepository extends JpaRepository<ProductsInCart, Long> {
}