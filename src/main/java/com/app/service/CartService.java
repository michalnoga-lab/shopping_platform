package com.app.service;

import com.app.Utilities.CustomAddresses;
import com.app.Utilities.FileUtilities;
import com.app.dto.CartDTO;
import com.app.dto.ProductDTO;
import com.app.exceptions.AppException;
import com.app.exceptions.ExceptionCodes;
import com.app.mappers.CartMapper;
import com.app.mappers.ProductMapper;
import com.app.model.*;
import com.app.parsers.XmlParserOptima;
import com.app.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final DeliveryAddressRepository deliveryAddressRepository;

    private final XmlParserOptima xmlParserOptima;
    private final EmailService emailService;

    public CartDTO getCart(Long cartId) {
        if (cartId == null) {
            throw new AppException(ExceptionCodes.SERVICE_CART, "getCart - cart ID is null");
        }
        if (cartId <= 0) {
            throw new AppException(ExceptionCodes.SERVICE_CART, "getCart - cart ID less than zero");
        }

        return cartRepository.findById(cartId)
                .stream()
                .map(CartMapper::toDto)
                .findFirst()
                .orElseThrow(() -> new AppException(ExceptionCodes.SERVICE_CART, "getCart - no cart with ID: " + cartId));
    }

    public CartDTO addProductToCart(ProductDTO productDTO, Long userId) {
        if (productDTO == null) {
            throw new AppException(ExceptionCodes.SERVICE_CART, "addProductToCart - product is null");
        }
        User user = userRepository.getOne(userId);
        Optional<CartDTO> cartDTOOptional = getActiveCart(userId);

        Product product = productRepository.getOne(productDTO.getId());
        product.setQuantity(productDTO.getQuantity());
        Cart cart;
        Optional<Cart> cartOptional = Optional.empty();

        if (cartDTOOptional.isPresent()) {
            cartOptional = cartRepository.findById(cartDTOOptional.get().getId());
        }

        cart = cartOptional.orElseGet(() -> Cart.builder().cartClosed(false).build());

        Set<Product> productsInCart = new HashSet<>();

        if (cart.getProducts() != null) {
            productsInCart = cart.getProducts();
        }

        if (productsInCart.contains(product)) {

            Product productAlreadyInCart = productsInCart
                    .stream()
                    .filter(prod -> prod.getId().equals(product.getId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("addProductToCart - no product in cart with ID: " + product.getId()));

            productAlreadyInCart.setQuantity(productAlreadyInCart.getQuantity() + product.getQuantity());

        } else {
            productsInCart.add(product);
        }

        cart.setProducts(productsInCart);

        CartDTO cartValues = calculateCartValue(productsInCart, user.getCompany().getDefaultPrice());
        cart.setTotalNetValue(cartValues.getTotalNetValue());
        cart.setTotalVatValue(cartValues.getTotalVatValue());
        cart.setTotalGrossValue(cartValues.getTotalGrossValue());

        cart.setUser(user);
        cartRepository.save(cart);
        return CartMapper.toDto(cart);
    }

    public CartDTO removeProductFromCart(Long productId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ExceptionCodes.SERVICE_CART, "removeProductFromCart - no user with ID: " + userId));

        Cart cart = cartRepository.findAllByUserId(userId)
                .stream()
                .filter(c -> c.getCartClosed().equals(false))
                .findFirst()
                .orElseThrow(() -> new AppException(ExceptionCodes.SERVICE_CART, "removeProductFromCart - no open cart for user with ID: " + userId));

        Set<Product> products = cart.getProducts()
                .stream()
                .filter(product -> !product.getId().equals(productId))
                .collect(Collectors.toSet());

        cart.setProducts(products);

        CartDTO cartValues = calculateCartValue(products, user.getCompany().getDefaultPrice());
        cart.setTotalNetValue(cartValues.getTotalNetValue());
        cart.setTotalVatValue(cartValues.getTotalVatValue());
        cart.setTotalGrossValue(cartValues.getTotalGrossValue());

        cartRepository.save(cart);
        return CartMapper.toDto(cart);
    }

    public CartDTO calculateCartValue(Set<Product> productsInCart, Price priceType) {
        if (productsInCart == null) {
            throw new AppException(ExceptionCodes.SERVICE_CART, "calculateCartValue - products set is null");
        }
        CartDTO cartDTO = new CartDTO();
        BigDecimal totalNetValue = BigDecimal.ZERO;
        BigDecimal totalVatValue = BigDecimal.ZERO;
        BigDecimal totalGrossValue = BigDecimal.ZERO;

        if (priceType.equals(Price.NET)) {
            for (Product product : productsInCart) {
                BigDecimal currentNetValue = product.getNettPrice().multiply(BigDecimal.valueOf(product.getQuantity()));
                BigDecimal currentVatValue = currentNetValue.multiply(BigDecimal.valueOf(product.getVat()));
                BigDecimal currentGrossValue = currentNetValue.add(currentVatValue);

                totalNetValue = totalNetValue.add(currentNetValue);
                totalVatValue = totalVatValue.add(currentVatValue);
                totalGrossValue = totalGrossValue.add(currentGrossValue);
            }
        } else {
            for (Product product : productsInCart) {
                BigDecimal currentGrossValue = product.getGrossPrice().multiply(BigDecimal.valueOf(product.getQuantity()));
                BigDecimal currentNetValue = currentGrossValue.divide(
                        (BigDecimal.ONE.setScale(2, RoundingMode.HALF_UP).add(BigDecimal.valueOf(
                                product.getVat()).setScale(2, RoundingMode.HALF_UP))));
                BigDecimal currentVatValue = currentGrossValue.subtract(currentNetValue);

                totalGrossValue = totalGrossValue.add(currentGrossValue);
                totalVatValue = totalVatValue.add(currentVatValue);
                totalNetValue = totalNetValue.add(currentNetValue);
            }
        }
        cartDTO.setTotalNetValue(totalNetValue.setScale(2, RoundingMode.HALF_UP));
        cartDTO.setTotalVatValue(totalVatValue.setScale(2, RoundingMode.HALF_UP));
        cartDTO.setTotalGrossValue(totalGrossValue.setScale(2, RoundingMode.HALF_UP));
        return cartDTO;
    }

    public List<CartDTO> getAllUsersCarts(Long userId) {
        if (userId == null) {
            throw new AppException(ExceptionCodes.SERVICE_CART, "getAllUsersCarts - ID is null");
        }
        List<CartDTO> usersCarts = new ArrayList<>();

        cartRepository.findAllByUserId(userId)
                .stream()
                .map(CartMapper::toDto)
                .forEach(usersCarts::add);

        return usersCarts;
    }

    public Optional<CartDTO> getActiveCart(Long userId) {
        if (userId == null) {
            throw new AppException(ExceptionCodes.SERVICE_CART, "getActiveCart - ID is null");
        }
        if (userId <= 0) {
            throw new AppException(ExceptionCodes.SERVICE_CART, "getActiveCart - ID less than zero");
        }
        List<CartDTO> allUserCarts = getAllUsersCarts(userId);

        if (allUserCarts.size() > 0) {
            List<CartDTO> notClosedCarts = allUserCarts
                    .stream()
                    .filter(cartDTO -> cartDTO.getCartClosed().equals(false))
                    .collect(Collectors.toList());

            if (notClosedCarts.size() > 0) {
                return Optional.of(notClosedCarts.get(0));
            }
        }
        return Optional.empty();
    }

    public CartDTO setAddressToCart(Long addressId, Long userId) {
        if (addressId == null) {
            throw new AppException(ExceptionCodes.SERVICE_CART, "setAddressToCart - ID is null");
        }
        if (addressId <= 0) {
            throw new AppException(ExceptionCodes.SERVICE_CART, "setAddressToCart - ID less than zero");
        }
        Optional<CartDTO> optionalCartDTO = getActiveCart(userId);
        CartDTO cartDTO = optionalCartDTO.orElseThrow(() -> new AppException(ExceptionCodes.SERVICE_CART, "setAddressToCart - cart Id is null"));
        Cart cart = cartRepository.getOne(cartDTO.getId());
        DeliveryAddress deliveryAddress = deliveryAddressRepository.getOne(addressId);
        cart.setDeliveryAddress(deliveryAddress);
        cartRepository.save(cart);
        return CartMapper.toDto(cart);
    }

    public CartDTO closeCart(Long userId) {
        if (userId == null) {
            throw new AppException(ExceptionCodes.SERVICE_CART, "closeCart - cart ID is null");
        }
        if (userId <= 0) {
            throw new AppException(ExceptionCodes.SERVICE_CART, "closeCart - cart ID less than zero");
        }

        Optional<CartDTO> cartDTOOptional = getActiveCart(userId);
        CartDTO cartDTO = cartDTOOptional.orElseThrow(() -> new AppException(ExceptionCodes.SERVICE_CART, "closeCart - no cart for user with ID: " + userId));
        Cart cart = cartRepository.getOne(cartDTO.getId());
        cart.setCartClosed(true);
        cart.setPurchaseTime(LocalDateTime.now());
        String fileName = FileUtilities.generateFileName(cartDTO.getUserDTO().getCompanyDTO().getNip());
        cart.setOrderNumber(fileName);

        cartRepository.save(cart);
        Set<ProductDTO> productsInCart = cart.getProducts()
                .stream()
                .map(ProductMapper::toDto)
                .collect(Collectors.toSet());

        String orderInXml = xmlParserOptima.generateXmlFileContent(cartDTO, productsInCart);
        String pathToFile = FileUtilities.saveFileToDisk(orderInXml, fileName);
        emailService.sendEmail(CustomAddresses.DEFAULT_DESTINATION_MAILBOX, "ZAMÓWIENIE", fileName, pathToFile);

        return CartMapper.toDto(cart);
    }

    public boolean userHasOpenCart(Long userId) {
        if (userId == null) {
            throw new AppException(ExceptionCodes.SERVICE_CART, "userHasOpenCart - cart ID is null");
        }
        if (userId <= 0) {
            throw new AppException(ExceptionCodes.SERVICE_CART, "userHasOpenCart - cart ID less than zero");
        }
        return getActiveCart(userId).isPresent();
    }
}