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

        Long auctionProductId1 = AuctionProductUtil.toId.apply(auctionProduct1);
        Long auctionProductId2 = AuctionProductUtil.toId.apply(auctionProduct2);
        Long auctionProductId3 = AuctionProductUtil.toId.apply(auctionProduct3);

        Long expectedId1 = 1L;
        Long expectedId2 = 2L;
        Long expectedId3 = 3L;

        assertThat(auctionProductId1).isEqualTo(expectedId1);
        assertThat(auctionProductId2).isEqualTo(expectedId2);
        assertThat(auctionProductId3).isEqualTo(expectedId3);
    }
}