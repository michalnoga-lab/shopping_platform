package com.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CartDTO {

    private Long id;
    private BigDecimal totalNetValue;
    private BigDecimal totalVatValue;
    private BigDecimal totalGrossValue;
    private Boolean cartClosed;
    private LocalDateTime purchaseTime;
    private DeliveryAddressDTO deliveryAddressDTO;
    private UserDTO userDTO;
    private String orderNumber;
    private Set<ProductsInCartDTO> productsInCartDTO;
}