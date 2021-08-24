package com.app.domain.auction_product.dto.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

@ExtendWith(SpringExtension.class)
public class CreateAuctionProductDtoValidatorTests {

    BigDecimal grossValue = BigDecimal.ONE.multiply(BigDecimal.valueOf(23 / 100)).add(BigDecimal.ONE);

    @Test
    void contextLoads() {
    }

    // TODO czy tutaj robi się testy
}