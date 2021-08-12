package com.app.domain.order;

import com.app.domain.order.dto.GetOrderDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
public class OrderTests {

    @Test
    void contextLoads() {
    }

    @Test
    @DisplayName("Order to OrderDto conversion")
    void toGetOrderDto() {

        var order = Order
                .builder()
                .id(1L)
                .userId(2L)
                .createdAt(LocalDateTime.now())
                .nettValue(BigDecimal.ZERO)
                .vatValue(BigDecimal.ZERO)
                .grossValue(BigDecimal.ZERO)
                .build();

        var expectedOrder = GetOrderDto
                .builder()
                .id(1L)
                .userId(2L)
                .createdAt(LocalDateTime.now())
                .nettValue(BigDecimal.ZERO)
                .vatValue(BigDecimal.ZERO)
                .grossValue(BigDecimal.ZERO)
                .build();

        var orderAfterChange = order.toGetOrderDto();

        assertThat(expectedOrder).isEqualTo(orderAfterChange);
    }
}