package com.app.dto;

import com.app.model.User;
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
public class CartDTO {

    private Long id;
    private BigDecimal totalNetValue;
    private BigDecimal totalVatValue;
    private BigDecimal totalGrossValue;
    private Boolean cartClosed;
    private LocalDateTime purchaseTime;
    private DeliveryAddressDTO deliveryAddressDTO;
    private UserDTO userDTO;
    private ProductDTO productDTO;
}