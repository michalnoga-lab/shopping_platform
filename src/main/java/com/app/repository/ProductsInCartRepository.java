package com.app.repository;

import com.app.model.Cart;
import com.app.model.ProductsInCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ProductsInCartRepository extends JpaRepository<ProductsInCart, Long> {

    Set<ProductsInCart> findProductsInCartById(Long cartId);
    Set<ProductsInCart> findAllByCart(Cart cart);

}