package com.app.domain.order;

import com.app.domain.cart_product.CartProduct;
import com.app.domain.order.dto.GetOrderDto;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Order class
 * Represents products purchased by user with quantity and whole order value
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
     * All products purchased in this order
     *
     * @see CartProduct
     */
    List<CartProduct> purchasedProducts;

    /**
     * Purchasing user ID
     *
     * @see com.app.domain.user.User
     */
    Long userId;

    /**
     * Date and time of purchase
     */
    LocalDateTime createdAt;

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

    /**
     * Converts Order to OrderDto
     *
     * @return GetOrderDto
     */
    public GetOrderDto toGetOrderDto() {
        return GetOrderDto
                .builder()
                .id(id)
                .userId(userId)
                .createdAt(createdAt)
                .nettValue(nettValue)
                .vatValue(vatValue)
                .grossValue(grossValue)
                .build();
    }
}