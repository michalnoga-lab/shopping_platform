package com.app.domain.order;

import com.app.domain.cart_product.CartProduct;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Create Order class
 * This class represents products purchased by user with quantity and whole order value
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode
@ToString
public class Order {

    /**
     * Order ID
     */
    Long id;

    /**
     * All products currently user purchased in this order
     */
    List<CartProduct> productsInCart;

    /**
     * Purchasing user ID
     *
     * @see com.app.domain.user.User
     */
    Long userId;

    /**
     * Date and time of purchase
     */
    LocalDateTime cratedAt;

    /**
     * Order whole nett value
     */
    BigDecimal nettValue;

    /**
     * Order whole VAT value
     */
    BigDecimal vatValue;

    /**
     * Order whole gross value
     */
    BigDecimal grossValue;
}