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
import com.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public void addProductToCart(ProductDTO productDTO, UserDTO userDTO) {
        if (productDTO == null) {
            throw new AppException(ExceptionCodes.SERVICE, "addProductToCart - product is null");
        }

        CartDTO cartDTO = getUsersCart(userDTO);
        Cart cart = CartMapper.fromDto(cartDTO);

        Set<Product> products = cart.getProducts();
        products.add(ProductMapper.fromDto(productDTO));
        cart.setCartClosed(false);
        cart.setProducts(products);
        cartRepository.saveAndFlush(cart);
    }

    public CartDTO getUsersCart(UserDTO userDTO) {
        if (userDTO == null) {
            throw new AppException(ExceptionCodes.SERVICE, "getUsersCart - login is null");
        }

        Optional<Cart> cartOptional = cartRepository
                .findAll()
                .stream()
                .filter(cart -> cart.getUser().getLogin().equals(userDTO.getLogin()))
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