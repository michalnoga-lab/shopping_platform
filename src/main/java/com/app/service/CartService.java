package com.app.service;

import com.app.dto.CartDTO;
import com.app.dto.ProductDTO;
import com.app.dto.UserDTO;
import com.app.exceptions.AppException;
import com.app.exceptions.ExceptionCodes;
import com.app.mappers.CartMapper;
import com.app.mappers.ProductMapper;
import com.app.mappers.UserMapper;
import com.app.model.Cart;
import com.app.model.Product;
import com.app.model.User;
import com.app.repository.CartRepository;
import com.app.repository.ProductRepository;
import com.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CartDTO addProductToCart(ProductDTO productDTO, Long userId) {
        if (productDTO == null) {
            throw new AppException(ExceptionCodes.SERVICE_CART, "addProductToCart - product is null");
        }

        User user = userRepository.getOne(userId);
        CartDTO cartDTO = getActiveCart(userId);
        Product product = productRepository.getOne(productDTO.getId());
        product.setQuantity(productDTO.getQuantity());
        Cart cart = CartMapper.fromDto(cartDTO);
        Set<Product> productsInCart = cart.getProducts();

        if (productsInCart.contains(product)) {

            Product productAlreadyInCart = productsInCart
                    .stream()
                    .filter(prod -> prod.getId().equals(product.getId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No product in cart with ID: " + product.getId()));

            productAlreadyInCart.setQuantity(productAlreadyInCart.getQuantity() + product.getQuantity());

        } else {
            productsInCart.add(product);
        }

        cart.setProducts(productsInCart);
        cart.setUser(user);
        cartRepository.save(cart);

        return CartMapper.toDto(cart);
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

    public CartDTO getActiveCart(Long userId) {
        if (userId == null) {
            throw new AppException(ExceptionCodes.SERVICE_CART, "getActiveCart - id is null");
        }
        if (userId < 0) {
            throw new AppException(ExceptionCodes.SERVICE_CART, "getActiveCart - id less than zero");
        }

        List<CartDTO> allUserCarts = getAllUsersCarts(userId);

        if (allUserCarts.size() > 0) {
            List<CartDTO> notClosedCarts = allUserCarts
                    .stream()
                    .filter(cartDTO -> cartDTO.getCartClosed().equals(false))
                    .collect(Collectors.toList());

            if (notClosedCarts.size() > 0) {
                return notClosedCarts.get(0);
            }
        }

        User user = userRepository.getOne(userId);
        return CartMapper.toDto(Cart.builder().user(user).build());
    }

    public CartDTO getCart(Long cartId) {
        if (cartId == null) {
            throw new AppException(ExceptionCodes.SERVICE_CART, "getCart - id is null");
        }
        if (cartId < 0) {
            throw new AppException(ExceptionCodes.SERVICE_CART, "getcart - id less than zero");
        }

        return cartRepository.findById(cartId)
                .stream()
                .map(CartMapper::toDto)
                .findFirst()
                .orElseThrow(() -> new AppException(ExceptionCodes.SERVICE_CART, "getCart - no cart with ID:" + cartId));
    }
}