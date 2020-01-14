package com.app.controllers;

import com.app.dto.CartDTO;
import com.app.service.CartService;
import com.app.service.ProductService;
import com.app.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {

    private final SecurityService securityService;
    private final CartService cartService;
    private final ProductService productService;

    @GetMapping("all")
    public String all(Model model) {
        List<CartDTO> carts = cartService.getAllUsersCarts(securityService.getLoggedInUserId());
        if (carts.size() == 0) {
            return "/carts/none";
        }
        model.addAttribute("carts", carts);
        return "carts/all";
    }

    @GetMapping("one/{id}")
    public String one(@PathVariable Long id, Model model) {
        model.addAttribute("cart", cartService.getCart(id));
        model.addAttribute("products", productService.getProductsOfCart(id));
        return "carts/one";
    }

    @GetMapping("one")
    public String activeOne(Model model) {
        Optional<CartDTO> cartDTOOptional = cartService.getActiveCart(securityService.getLoggedInUserId());

        if (cartDTOOptional.isEmpty()) {
            return "carts/noneActive";
        }
        model.addAttribute("cart", cartDTOOptional.get());
        model.addAttribute("products", productService.getProductsOfCart(cartDTOOptional.get().getId()));
        return "carts/one";
    }

    @GetMapping("closed")
    public String closeActiveCart() {

        // TODO: 2020-01-14 przesłanie zamówienia do realizacji

        return "carts/closed";
    }
}