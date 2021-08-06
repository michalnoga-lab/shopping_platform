package com.app.domain.order;

import com.app.domain.cart_product.CartProduct;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode
@ToString
public class Order {

    Long id;
    List<CartProduct> productsInCart;
    BigDecimal nettValue;
    BigDecimal vatValue;
    BigDecimal grossValue;

    public Order withAddedProductToCart(CartProduct newCartProduct) {
        List<CartProduct> updatedProductsInCart = productsInCart;
        updatedProductsInCart.add(newCartProduct);

        return Order
                .builder()
                .id(id)
                .productsInCart(updatedProductsInCart)
                .nettValue(updatedProductsInCart.stream().map(CartProduct::getNettValue).reduce(BigDecimal.ZERO, BigDecimal::add))
                .vatValue(updatedProductsInCart.stream().map(CartProduct::getVatValue).reduce(BigDecimal.ZERO, BigDecimal::add))
                .grossValue(updatedProductsInCart.stream().map(CartProduct::getGrossValue).reduce(BigDecimal.ZERO, BigDecimal::add))
                .build();
    }
}