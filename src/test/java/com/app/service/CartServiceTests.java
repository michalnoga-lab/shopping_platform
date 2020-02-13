package com.app.service;

import com.app.Utilities.FileUtilities;
import com.app.dto.CartDTO;
import com.app.dto.CompanyDTO;
import com.app.dto.DeliveryAddressDTO;
import com.app.exceptions.AppException;
import com.app.mappers.CartMapper;
import com.app.mappers.CompanyMapper;
import com.app.mappers.UserMapper;
import com.app.model.*;
import com.app.parsers.XmlParserOptima;
import com.app.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@ExtendWith(SpringExtension.class)
public class CartServiceTests {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeliveryAddressRepository deliveryAddressRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private XmlParserOptima xmlParserOptima;

    @Autowired
    private EmailService emailService;

    @Autowired
    private FileUtilities fileUtilities;

    @TestConfiguration
    public static class AppTestConfiguration {

        @MockBean
        private CartRepository cartRepository;

        @MockBean
        private UserRepository userRepository;

        @MockBean
        private DeliveryAddressRepository deliveryAddressRepository;

        @MockBean
        private ProductRepository productRepository;

        @MockBean
        private XmlParserOptima xmlParserOptima;

        @MockBean
        private EmailService emailService;

        @MockBean
        private FileUtilities fileUtilities;

        @Bean
        public CartService cartService() {
            return new CartService(cartRepository, userRepository,
                    productRepository, deliveryAddressRepository, xmlParserOptima, emailService);
        }
    }

    @Test
    @DisplayName("getCart - no cart in DB with searching ID")
    void test10() {

        AppException appException = Assertions.assertThrows(
                AppException.class, () -> cartService.getCart(5L));

        Assertions.assertEquals("getCart - no cart with ID: 5", appException.getDescription());
    }

    @Test
    @DisplayName("getCart - cart ID is null")
    void test11() {

        AppException appException = Assertions.assertThrows(
                AppException.class, () -> cartService.getCart(null));

        Assertions.assertEquals("getCart - cart ID is null", appException.getDescription());
    }

    @Test
    @DisplayName("getCart - cart ID equals or less than zero")
    void test12() {

        AppException appException1 = Assertions.assertThrows(
                AppException.class, () -> cartService.getCart(0L));

        AppException appException2 = Assertions.assertThrows(
                AppException.class, () -> cartService.getCart(-1L));

        Assertions.assertEquals("getCart - cart ID less than zero", appException1.getDescription());
        Assertions.assertEquals("getCart - cart ID less than zero", appException2.getDescription());
    }

    @Test
    @DisplayName("getCart - one cart in DB")
    void test13() {

        Cart cart = Cart.builder().id(1L).build();
        Optional<Cart> cartOptional = Optional.of(cart);

        Mockito
                .when(cartRepository.findById(cart.getId()))
                .thenReturn(cartOptional);

        CartDTO actualCart = cartService.getCart(cart.getId());

        Assertions.assertEquals(CartMapper.toDto(cart), actualCart);
    }

    @Test
    @DisplayName("getCart - many carts in DB")
    void test14() {

        Cart cart1 = Cart.builder().id(1L).build();
        Cart cart2 = Cart.builder().id(1L).build();
        Cart cart3 = Cart.builder().id(1L).build();
        Optional<Cart> cartOptional = Optional.of(cart1);
        List<Cart> cartList = List.of(cart1, cart2, cart3);

        Mockito
                .when(cartRepository.findAll())
                .thenReturn(cartList);

        Mockito
                .when(cartRepository.findById(cart1.getId()))
                .thenReturn(cartOptional);

        CartDTO actualCart = cartService.getCart(cart1.getId());

        Assertions.assertEquals(CartMapper.toDto(cart1), actualCart);
    }

    @Test
    @DisplayName("addProductToCart")
    void test20() {

    }

    @Test
    @DisplayName("calculateCartValue - nett price type - no products in cart")
    void test30() {

        CartDTO expectedCart = CartDTO.builder()
                .totalNetValue(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP))
                .totalVatValue(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP))
                .totalGrossValue(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP))
                .build();

        Set<Product> products = Set.of();

        CartDTO actualCart = cartService.calculateCartValue(products, Price.NET);

        Assertions.assertEquals(expectedCart, actualCart);
    }

    @Test
    @DisplayName("calculateCartValue - nett price type - one product")
    void test31() {

        Product product = Product.builder().id(1L)
                .nettPrice(BigDecimal.valueOf(100))
                .vat(0.23)
                .grossPrice(BigDecimal.valueOf(123))
                .quantity(10)
                .build();

        CartDTO expectedCart = CartDTO.builder()
                .totalNetValue(BigDecimal.valueOf(1000).setScale(2, RoundingMode.HALF_UP))
                .totalVatValue(BigDecimal.valueOf(230).setScale(2, RoundingMode.HALF_UP))
                .totalGrossValue(BigDecimal.valueOf(1230).setScale(2, RoundingMode.HALF_UP))
                .build();

        Set<Product> products = Set.of(product);

        CartDTO actualCart = cartService.calculateCartValue(products, Price.NET);

        Assertions.assertEquals(expectedCart, actualCart);
    }

    @Test
    @DisplayName("calculateCartValue - nett price type - many products")
    void test32() {

        Product product1 = Product.builder().id(1L)
                .nettPrice(BigDecimal.valueOf(100))
                .vat(0.23)
                .grossPrice(BigDecimal.valueOf(100 + 23))
                .quantity(5)
                .build();

        Product product2 = Product.builder().id(2L)
                .nettPrice(BigDecimal.valueOf(27))
                .vat(0.23)
                .grossPrice(BigDecimal.valueOf(27 + 6.21))
                .quantity(30)
                .build();

        Product product3 = Product.builder().id(3L)
                .nettPrice(BigDecimal.valueOf(342))
                .vat(0.08)
                .grossPrice(BigDecimal.valueOf(342 + 27.36))
                .quantity(60)
                .build();

        Product product4 = Product.builder().id(4L)
                .nettPrice(BigDecimal.valueOf(5000))
                .vat(0.23)
                .grossPrice(BigDecimal.valueOf(5000 + 1150))
                .quantity(600)
                .build();

        CartDTO expectedCart = CartDTO.builder()
                .totalNetValue(BigDecimal.valueOf(100 * 5 + 30 * 27 + 60 * 342 + 600 * 5000)
                        .setScale(2, RoundingMode.HALF_UP))
                .totalVatValue(BigDecimal.valueOf(100 * 5 * 0.23 + 30 * 27 * 0.23 + 60 * 342 * 0.08 + 600 * 5000 * 0.23)
                        .setScale(2, RoundingMode.HALF_UP))
                .totalGrossValue(BigDecimal.valueOf(
                        (100 * 5 * 0.23 + 100 * 5) + (30 * 27 * 0.23 + 30 * 27) +
                                (60 * 342 * 0.08 + 60 * 342) + (600 * 5000 * 0.23 + 600 * 5000))
                        .setScale(2, RoundingMode.HALF_UP))
                .build();

        Set<Product> products = Set.of(product1, product2, product3, product4);

        CartDTO actualCart = cartService.calculateCartValue(products, Price.NET);

        Assertions.assertEquals(expectedCart, actualCart);
    }

    @Test
    @DisplayName("calculateCartValue - gross price type - no products in cart")
    void test40() {

        CartDTO expectedCart = CartDTO.builder()
                .totalNetValue(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP))
                .totalVatValue(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP))
                .totalGrossValue(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP))
                .build();

        Set<Product> products = Set.of();

        CartDTO actualCart = cartService.calculateCartValue(products, Price.GROSS);

        Assertions.assertEquals(expectedCart, actualCart);
    }

    @Test
    @DisplayName("calculateCartValue - gross price type - one product")
    void test41() {

        Product product = Product.builder().id(1L)
                .nettPrice(BigDecimal.valueOf(100))
                .vat(0.23)
                .grossPrice(BigDecimal.valueOf(123))
                .quantity(10)
                .build();

        CartDTO expectedCart = CartDTO.builder()
                .totalNetValue(BigDecimal.valueOf(1000).setScale(2, RoundingMode.HALF_UP))
                .totalVatValue(BigDecimal.valueOf(230).setScale(2, RoundingMode.HALF_UP))
                .totalGrossValue(BigDecimal.valueOf(1230).setScale(2, RoundingMode.HALF_UP))
                .build();

        Set<Product> products = Set.of(product);

        CartDTO actualCart = cartService.calculateCartValue(products, Price.GROSS);

        Assertions.assertEquals(expectedCart, actualCart);
    }

    @Test
    @DisplayName("calculateCartValue - gross price type - many products")
    void test42() {

        Product product1 = Product.builder().id(1L)
                .nettPrice(BigDecimal.valueOf(100))
                .vat(0.23)
                .grossPrice(BigDecimal.valueOf(100 + 23))
                .quantity(5)
                .build();

        Product product2 = Product.builder().id(2L)
                .nettPrice(BigDecimal.valueOf(27))
                .vat(0.23)
                .grossPrice(BigDecimal.valueOf(27 + 6.21))
                .quantity(30)
                .build();

        Product product3 = Product.builder().id(3L)
                .nettPrice(BigDecimal.valueOf(342))
                .vat(0.08)
                .grossPrice(BigDecimal.valueOf(342 + 27.36))
                .quantity(60)
                .build();

        Product product4 = Product.builder().id(4L)
                .nettPrice(BigDecimal.valueOf(5000))
                .vat(0.23)
                .grossPrice(BigDecimal.valueOf(5000 + 1150))
                .quantity(600)
                .build();

        CartDTO expectedCart = CartDTO.builder()
                .totalNetValue(BigDecimal.valueOf(100 * 5 + 30 * 27 + 60 * 342 + 600 * 5000)
                        .setScale(2, RoundingMode.HALF_UP))
                .totalVatValue(BigDecimal.valueOf(100 * 5 * 0.23 + 30 * 27 * 0.23 + 60 * 342 * 0.08 + 600 * 5000 * 0.23)
                        .setScale(2, RoundingMode.HALF_UP))
                .totalGrossValue(BigDecimal.valueOf(
                        (100 * 5 * 0.23 + 100 * 5) + (30 * 27 * 0.23 + 30 * 27) +
                                (60 * 342 * 0.08 + 60 * 342) + (600 * 5000 * 0.23 + 600 * 5000))
                        .setScale(2, RoundingMode.HALF_UP))
                .build();

        Set<Product> products = Set.of(product1, product2, product3, product4);

        CartDTO actualCart = cartService.calculateCartValue(products, Price.GROSS);

        Assertions.assertEquals(expectedCart, actualCart);
    }

    @Test
    @DisplayName("getAllUsersCarts - user has no carts")
    void test50() {

        Mockito
                .when(cartRepository.findAll())
                .thenReturn(List.of());

        List<CartDTO> expectedCarts = List.of();

        Assertions.assertEquals(expectedCarts, cartService.getAllUsersCarts(1L));
    }

    @Test
    @DisplayName("getAllUsersCarts - user has one open cart")
    void test51() {

        User user = User.builder().id(1L).build();
        Optional<User> userOptional = Optional.of(user);
        Cart cart = Cart.builder().id(2L).cartClosed(false).build();
        List<Cart> carts = List.of(cart);

        Mockito
                .when(userRepository.findById(1L))
                .thenReturn(userOptional);

        Mockito
                .when(cartRepository.findAllByUserId(1L))
                .thenReturn(carts);

        List<CartDTO> expectedCarts = List.of(CartMapper.toDto(cart));

        Assertions.assertEquals(expectedCarts, cartService.getAllUsersCarts(user.getId()));
    }

    @Test
    @DisplayName("getAllUsersCarts - user has one closed cart")
    void test52() {

        User user = User.builder().id(1L).build();
        Optional<User> userOptional = Optional.of(user);
        Cart cart = Cart.builder().id(2L).cartClosed(true).build();
        List<Cart> carts = List.of(cart);

        Mockito
                .when(userRepository.findById(1L))
                .thenReturn(userOptional);

        Mockito
                .when(cartRepository.findAllByUserId(1L))
                .thenReturn(carts);

        List<CartDTO> expectedCarts = List.of(CartMapper.toDto(cart));

        Assertions.assertEquals(expectedCarts, cartService.getAllUsersCarts(user.getId()));
    }

    @Test
    @DisplayName("getAllUsersCarts - user has many open and closed carts")
    void test53() {

        User user = User.builder().id(1L).build();
        Optional<User> userOptional = Optional.of(user);

        Cart cart2 = Cart.builder().id(2L).cartClosed(true).build();
        Cart cart3 = Cart.builder().id(3L).cartClosed(false).build();
        Cart cart4 = Cart.builder().id(4L).cartClosed(false).build();
        Cart cart5 = Cart.builder().id(5L).cartClosed(false).build();
        Cart cart6 = Cart.builder().id(6L).cartClosed(true).build();
        Cart cart7 = Cart.builder().id(7L).cartClosed(true).build();

        List<Cart> carts = List.of(cart2, cart3, cart4, cart5, cart6, cart7);

        Mockito
                .when(userRepository.findById(1L))
                .thenReturn(userOptional);

        Mockito
                .when(cartRepository.findAllByUserId(1L))
                .thenReturn(carts);

        List<CartDTO> expectedCarts = List.of(
                CartMapper.toDto(cart2),
                CartMapper.toDto(cart3),
                CartMapper.toDto(cart4),
                CartMapper.toDto(cart5),
                CartMapper.toDto(cart6),
                CartMapper.toDto(cart7));

        Assertions.assertEquals(expectedCarts, cartService.getAllUsersCarts(user.getId()));
    }

    @Test
    @DisplayName("getActiveCart - user has no carts")
    void test60() {

        User user = User.builder().id(1L).build();
        Optional<User> userOptional = Optional.of(user);

        List<Cart> carts = List.of();

        Mockito
                .when(userRepository.findById(user.getId()))
                .thenReturn(userOptional);

        Mockito
                .when(cartRepository.findAll())
                .thenReturn(carts);

        Optional<CartDTO> expectedCart = Optional.empty();
        Optional<CartDTO> actualCart = cartService.getActiveCart(user.getId());

        Assertions.assertEquals(expectedCart, actualCart);
    }

    @Test
    @DisplayName("getActiveCart - user has no open carts")
    void test61() {

        User user = User.builder().id(1L).build();
        Optional<User> userOptional = Optional.of(user);

        Cart cart2 = Cart.builder().id(2L).cartClosed(true).build();
        Cart cart3 = Cart.builder().id(3L).cartClosed(true).build();
        Cart cart4 = Cart.builder().id(4L).cartClosed(true).build();
        Cart cart5 = Cart.builder().id(5L).cartClosed(true).build();
        Cart cart6 = Cart.builder().id(6L).cartClosed(true).build();
        Cart cart7 = Cart.builder().id(7L).cartClosed(true).build();

        List<Cart> carts = List.of(cart2, cart3, cart4, cart5, cart6, cart7);

        Mockito
                .when(userRepository.findById(user.getId()))
                .thenReturn(userOptional);

        Mockito
                .when(cartRepository.findAll())
                .thenReturn(carts);

        Optional<CartDTO> expectedCart = Optional.empty();
        Optional<CartDTO> actualCart = cartService.getActiveCart(user.getId());

        Assertions.assertEquals(expectedCart, actualCart);
    }

    @Test
    @DisplayName("getActiveCart - user has one open cart (last)")
    void test62() {

        User user = User.builder().id(1L).build();
        Optional<User> userOptional = Optional.of(user);

        Cart cart2 = Cart.builder().id(2L).cartClosed(true).user(user).build();
        Cart cart3 = Cart.builder().id(3L).cartClosed(true).user(user).build();
        Cart cart4 = Cart.builder().id(4L).cartClosed(true).user(user).build();
        Cart cart5 = Cart.builder().id(5L).cartClosed(true).user(user).build();
        Cart cart6 = Cart.builder().id(6L).cartClosed(true).user(user).build();
        Cart cart7 = Cart.builder().id(7L).cartClosed(false).user(user).build();

        List<Cart> carts = List.of(cart2, cart3, cart4, cart5, cart6, cart7);

        Mockito
                .when(userRepository.findById(user.getId()))
                .thenReturn(userOptional);

        Mockito
                .when(cartRepository.findAllByUserId(user.getId()))
                .thenReturn(carts);

        Optional<CartDTO> expectedCart = Optional.of(CartMapper.toDto(cart7));
        Optional<CartDTO> actualCart = cartService.getActiveCart(user.getId());

        Assertions.assertEquals(expectedCart, actualCart);
    }

    @Test
    @DisplayName("getActiveCart - user has one open cart (middle)")
    void test63() {

        User user = User.builder().id(1L).build();
        Optional<User> userOptional = Optional.of(user);

        Cart cart2 = Cart.builder().id(2L).cartClosed(true).user(user).build();
        Cart cart3 = Cart.builder().id(3L).cartClosed(true).user(user).build();
        Cart cart4 = Cart.builder().id(4L).cartClosed(false).user(user).build();
        Cart cart5 = Cart.builder().id(5L).cartClosed(true).user(user).build();
        Cart cart6 = Cart.builder().id(6L).cartClosed(true).user(user).build();
        Cart cart7 = Cart.builder().id(7L).cartClosed(true).user(user).build();

        List<Cart> carts = List.of(cart2, cart3, cart4, cart5, cart6, cart7);

        Mockito
                .when(userRepository.findById(user.getId()))
                .thenReturn(userOptional);

        Mockito
                .when(cartRepository.findAllByUserId(user.getId()))
                .thenReturn(carts);

        Optional<CartDTO> expectedCart = Optional.of(CartMapper.toDto(cart4));
        Optional<CartDTO> actualCart = cartService.getActiveCart(user.getId());

        Assertions.assertEquals(expectedCart, actualCart);
    }

    @Test
    @DisplayName("getActiveCart - user has one open cart (first)")
    void test64() {

        User user = User.builder().id(1L).build();
        Optional<User> userOptional = Optional.of(user);

        Cart cart2 = Cart.builder().id(2L).cartClosed(false).user(user).build();
        Cart cart3 = Cart.builder().id(3L).cartClosed(true).user(user).build();
        Cart cart4 = Cart.builder().id(4L).cartClosed(true).user(user).build();
        Cart cart5 = Cart.builder().id(5L).cartClosed(true).user(user).build();
        Cart cart6 = Cart.builder().id(6L).cartClosed(true).user(user).build();
        Cart cart7 = Cart.builder().id(7L).cartClosed(true).user(user).build();

        List<Cart> carts = List.of(cart2, cart3, cart4, cart5, cart6, cart7);

        Mockito
                .when(userRepository.findById(user.getId()))
                .thenReturn(userOptional);

        Mockito
                .when(cartRepository.findAllByUserId(user.getId()))
                .thenReturn(carts);

        Optional<CartDTO> expectedCart = Optional.of(CartMapper.toDto(cart2));
        Optional<CartDTO> actualCart = cartService.getActiveCart(user.getId());

        Assertions.assertEquals(expectedCart, actualCart);
    }

    @Test
    @DisplayName("setAddressToCart")
    void test70() {

        User user = User.builder().id(1L).build();
        DeliveryAddress deliveryAddress = DeliveryAddress.builder().id(2L).build();
        DeliveryAddressDTO deliveryAddressDTO = DeliveryAddressDTO.builder().id(2L).build();
        CartDTO cartDTO = CartDTO.builder().id(3L).cartClosed(false).build();
        Cart cart = Cart.builder().id(3L).cartClosed(false).build();
        List<Cart> carts = List.of(cart);

        Mockito
                .when(cartRepository.findAllByUserId(user.getId()))
                .thenReturn(carts);

        Mockito
                .when(cartRepository.getOne(cartDTO.getId()))
                .thenReturn(cart);

        Mockito
                .when(deliveryAddressRepository.getOne(deliveryAddress.getId()))
                .thenReturn(deliveryAddress);

        cartDTO.setDeliveryAddressDTO(deliveryAddressDTO);
        CartDTO actualCart = cartService.setAddressToCart(deliveryAddress.getId(), user.getId());

        Assertions.assertEquals(cartDTO, actualCart);
    }

    @Test
    @DisplayName("closeCart")
    void test80() {

        CompanyDTO companyDTO = CompanyDTO.builder().nip("0000000000").build();
        User user = User.builder()
                .id(1L)
                .company(CompanyMapper.fromDto(companyDTO))
                .build();

        CartDTO cartDTO = CartDTO.builder()
                .id(3L)
                .cartClosed(false)
                .userDTO(UserMapper.toDto(user))
                .build();

        Cart cart = Cart.builder().id(3L).products(Set.of()).cartClosed(false).build();
        List<Cart> carts = List.of(cart);

        Mockito
                .when(cartRepository.findAllByUserId(user.getId()))
                .thenReturn(carts);

        Mockito
                .when(cartRepository.getOne(cartDTO.getId()))
                .thenReturn(cart);

       /* java.lang.NullPointerException
        at com.app.service.CartService.closeCart(CartService.java:236)
        at com.app.service.CartServiceTests.test80(CartServiceTests.java:648)

        cartDTO.setCartClosed(true);
        CartDTO actualCart = cartService.closeCart(user.getId());
        actualCart.setPurchaseTime(null);

        Assertions.assertEquals(cartDTO, actualCart);*/
    }
}