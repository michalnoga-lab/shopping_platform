package com.app.domain.auction_product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
public class AuctionProductUtilTests {

    @Test
    void contextLoads() {
    }

    @Test
    @DisplayName("toId")
    void toId() {
        AuctionProduct auctionProduct1 = AuctionProduct.builder().id(1L).build();
        AuctionProduct auctionProduct2 = AuctionProduct.builder().id(2L).build();
        AuctionProduct auctionProduct3 = AuctionProduct.builder().id(3L).build();

        // TODO
    }
}