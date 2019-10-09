package com.app.dto;

import com.app.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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
    private DeliveryAddressDTO deliveryAddressDTO;
    private User user; // TODO: 2019-10-09 ma być DTO
}