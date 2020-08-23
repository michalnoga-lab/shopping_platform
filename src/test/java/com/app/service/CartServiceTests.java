package com.app.service;

import com.app.Utilities.FileManager;
import com.app.dto.CartDTO;
import com.app.dto.CompanyDTO;
import com.app.dto.DeliveryAddressDTO;
import com.app.dto.ProductDTO;
import com.app.exceptions.AppException;
import com.app.mappers.CartMapper;
import com.app.mappers.CompanyMapper;
import com.app.mappers.ProductMapper;
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
    private ProductsInCartRepository productsInCartRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private XmlParserOptima xmlParserOptima;

    @Autowired
    private EmailService emailService;

    @Autowired
    private FileManager fileManager;

    @TestConfiguration
    public static class AppTestConfiguration {

        @MockBean
        private CartRepository cartRepository;

        @MockBean
        private UserRepository userRepository;

        @MockBean
        private ProductRepository productRepository;

        @MockBean
        private DeliveryAddressRepository deliveryAddressRepository;

        @MockBean
        private ProductsInCartRepository productsInCartRepository;

        @MockBean
        private XmlParserOptima xmlParserOptima;

        @MockBean
        private EmailService emailService;

        @MockBean
        private FileManager fileManager;

        @Bean
        public CartService cartService() {
            return new CartService(cartRepository, userRepository, productRepository,
                    deliveryAddressRepository, productsInCartRepository, xmlParserOptima, emailService);
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
    @DisplayName("addProductToCart - check cart value - no products in cart")
    void test20() {
        // TODO: 23.08.2020 dokończyć - nie działa wartość VAT

        Cart cart = Cart.builder().id(1L).cartClosed(false).build();

        Product product = Product.builder()
                .id(2L)
                .name("name")
                .quantity(50)
                .nettPrice(BigDecimal.valueOf(10.00))
                .vat(23.00)
                .grossPrice(BigDecimal.valueOf(12.30))
                .hidden(false)
                .build();

        User user = User.builder().id(3L).build();

        Mockito
                .when(productRepository.findAll())
                .thenReturn(List.of(product));

        Mockito
                .when(cartRepository.findAll())
                .thenReturn(List.of(cart));

        Mockito
                .when(productRepository.getOne(2L))
                .thenReturn(product);

        CartDTO cartDTO = cartService.addProductToCart(ProductMapper.toDto(product), user.getId());

        Assertions.assertEquals(BigDecimal.valueOf(500).setScale(2, RoundingMode.HALF_UP),
                cartDTO.getTotalNetValue());
        Assertions.assertEquals(BigDecimal.valueOf(115).setScale(2, RoundingMode.HALF_UP),
                cartDTO.getTotalVatValue());
        Assertions.assertEquals(BigDecimal.valueOf(615).setScale(2, RoundingMode.HALF_UP),
                cartDTO.getTotalGrossValue());
    }

    @Test
    @DisplayName("calculateCartNettValue")
    void test30() {

        BigDecimal actualValue = cartService.calculateCartNettValue(BigDecimal.TEN, 10);
        Assertions.assertEquals(BigDecimal.valueOf(100).setScale(2, RoundingMode.HALF_UP), actualValue);
    }

    @Test
    @DisplayName("calculateCartNettValue")
    void test31() {

        BigDecimal actualValue = cartService.calculateCartNettValue(BigDecimal.TEN, 100);
        Assertions.assertEquals(BigDecimal.valueOf(1000).setScale(2, RoundingMode.HALF_UP), actualValue);
    }

    @Test
    @DisplayName("calculateCartNettValue")
    void test32() {

        BigDecimal actualValue = cartService.calculateCartNettValue(BigDecimal.TEN, 50);
        Assertions.assertEquals(BigDecimal.valueOf(500).setScale(2, RoundingMode.HALF_UP), actualValue);
    }

    @Test
    @DisplayName("calculateCartVatValue")
    void test40() {

        BigDecimal actualValue = cartService.calculateCartVatValue(BigDecimal.TEN, 23.00);
        Assertions.assertEquals(BigDecimal.valueOf(2.30).setScale(2, RoundingMode.HALF_UP), actualValue);
    }

    @Test
    @DisplayName("calculateCartVatValue")
    void test41() {

        BigDecimal actualValue = cartService.calculateCartVatValue(BigDecimal.ZERO, 23.00);
        Assertions.assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP), actualValue);
    }

    @Test
    @DisplayName("calculateCartVatValue")
    void test42() {

        BigDecimal actualValue = cartService.calculateCartVatValue(BigDecimal.valueOf(100), 23.00);
        Assertions.assertEquals(BigDecimal.valueOf(23).setScale(2, RoundingMode.HALF_UP), actualValue);
    }

    @Test
    @DisplayName("calculateCartGrossValue")
    void test50() {

        BigDecimal actualValue = cartService.calculateTotalGrossValue(BigDecimal.TEN, BigDecimal.valueOf(2.30));
        Assertions.assertEquals(BigDecimal.valueOf(12.30).setScale(2, RoundingMode.HALF_UP), actualValue);
    }

    @Test
    @DisplayName("calculateCartGrossValue")
    void test51() {

        BigDecimal actualValue = cartService.calculateTotalGrossValue(BigDecimal.valueOf(100), BigDecimal.valueOf(23));
        Assertions.assertEquals(BigDecimal.valueOf(123).setScale(2, RoundingMode.HALF_UP), actualValue);
    }

    @Test
    @DisplayName("calculateCartGrossValue")
    void test52() {

        BigDecimal actualValue = cartService.calculateTotalGrossValue(BigDecimal.valueOf(200), BigDecimal.valueOf(46));
        Assertions.assertEquals(BigDecimal.valueOf(246).setScale(2, RoundingMode.HALF_UP), actualValue);
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

        Cart cart = Cart.builder().id(3L).cartClosed(false).build();
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