package com.app.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class GetOrderDto {

    /**
     * Order ID
     */
    Long id;

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
}