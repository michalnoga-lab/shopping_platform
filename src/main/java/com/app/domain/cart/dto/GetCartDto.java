package com.app.domain.cart.dto;

import com.app.domain.cart_product.CartProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * GetCartDto class
 * Returns Cart as DTO
 *
 * @see com.app.domain.cart.Cart
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class GetCartDto {

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