package com.app.domain.cart;

import com.app.domain.cart_product.CartProduct;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Cart class
 * Represents user's cart
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode
@ToString
public class Cart {

    /**
     * Cart ID
     */
    Long id;

    /**
     * User's owning this cart ID
     *
     * @see com.app.domain.user.User
     */
    Long userId;

    /**
     * Products currently in cart
     *
     * @see CartProduct
     */
    List<CartProduct> productsInCart;

    /**
     * Cart current nett value
     */
    BigDecimal nettValue;

    /**
     * Cart current vat value
     */
    BigDecimal vatValue;

    /**
     * Cart current gross value
     */
    BigDecimal grossPrice;
}