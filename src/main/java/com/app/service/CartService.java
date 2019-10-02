package com.app.service;

import com.app.dto.CartDTO;
import com.app.dto.ProductDTO;
import com.app.dto.UserDTO;
import com.app.exceptions.AppException;
import com.app.exceptions.ExceptionCodes;
import com.app.mappers.CartMapper;
import com.app.mappers.ProductMapper;
import com.app.model.Cart;
import com.app.model.Product;
import com.app.model.User;
import com.app.repository.CartRepository;
import com.app.repository.ProductRepository;
import com.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.desktop.AppReopenedEvent;
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

    public CartDTO addProductToCart(ProductDTO productDTO, UserDTO userDTO) {
        if (productDTO == null) {
            throw new AppException(ExceptionCodes.SERVICE, "addProductToCart - product is null");
        }
        CartDTO cartDTO = getUsersCart(userDTO);
        Cart cart = CartMapper.fromDto(cartDTO);

        Set<Product> productsInCart = cart.getProducts();
        Optional<Product> productOptional = productRepository
                .findAll()
                .stream()
                .filter(product -> product.getId().equals(productDTO.getId()))
                .findFirst();

        if (productOptional.isPresent()) {
            if (productsInCart.contains(productOptional.get())) {
                Product product = productsInCart
                        .stream()
                        .filter(p -> p.getId().equals(productOptional.get().getId()))
                        .findFirst()
                        .orElseThrow(() -> new AppException(ExceptionCodes.SERVICE,
                                "addProductToCart - no product in cart with ID: " + productOptional.get().getId()));
                product.setQuantity(product.getQuantity() + productDTO.getQuantity());
                productsInCart.add(product);
            } else {
                productsInCart.add(productOptional.get());
            }
        }

        cart.setCartClosed(false);
        cart.setProducts(productsInCart);
        cartRepository.saveAndFlush(cart);

        return CartMapper.toDto(cart);
    }

    public List<CartDTO> getAllUsersCarts(UserDTO userDTO) {
        if (userDTO == null) {
            throw new AppException(ExceptionCodes.SERVICE, "getAllUsersCarts - login is null");
        }

        return cartRepository
                .findAll()
                .stream()
                .filter(cart -> cart.getUser().getId().equals(userDTO.getId()))
                .map(CartMapper::toDto)
                .collect(Collectors.toList());
    }

    public CartDTO getUsersCart(UserDTO userDTO) {
        if (userDTO == null) {
            throw new AppException(ExceptionCodes.SERVICE, "getUsersCart - login is null");
        }

        Optional<Cart> cartOptional = cartRepository
                .findAll()
                .stream()
                .filter(cart -> cart.getUser().getId().equals(userDTO.getId()))
                .filter(cart -> cart.getCartClosed().equals(false))
                .findFirst();

        if (cartOptional.isEmpty()) {
            Optional<User> userOptional = userRepository
                    .findAll()
                    .stream()
                    .filter(user -> user.getLogin().equals(userDTO.getLogin()))
                    .findFirst();

            if (userOptional.isPresent()) {
                Cart cart = Cart.builder().build();
                cart.setUser(userOptional.get());
                cartRepository.save(cart);
                return CartMapper.toDto(cart);
            }
        }
        return CartMapper.toDto(cartOptional.get());
    }
}