package com.app.domain.auction_product;

import com.app.domain.auction_product.dto.GetAuctionProductDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

@ExtendWith(SpringExtension.class)
public class AuctionProductTests {

    @Test
    void contextLoads() {
    }

    @Test
    @DisplayName("AuctionProduct to AuctionProductDto conversion")
    public void toGetAuctionProductDto() {

        BigDecimal grossValue = BigDecimal.ONE.multiply(BigDecimal.valueOf(23 / 100)).add(BigDecimal.ONE);

        var auctionProduct = AuctionProduct
                .builder()
                .id(1L)
                .name("name")
                .description("description")
                .auctionIndex("auctionIndex")
                .auctionConsecutiveNumber("auctionConsecutiveNumber")
                .nettPrice(BigDecimal.ONE)
                .vat(23)
                .grossPrice(grossValue)
                .ean("1234567890")
                .build();

        var expectedAuctionProduct = GetAuctionProductDto
                .builder()
                .id(1L)
                .name("name")
                .description("description")
                .auctionIndex("auctionIndex")
                .auctionConsecutiveNumber("auctionConsecutiveNumber")
                .nettPrice(BigDecimal.ONE)
                .vat(23)
                .grossPrice(grossValue)
                .ean("1234567890")
                .build();

        var auctionProductAfterChange = auctionProduct.toGetAuctionProductDto();

        assertThat(expectedAuctionProduct).isEqualTo(auctionProductAfterChange);
    }
}