package com.app.controllers;

import com.app.service.CartService;
import com.app.service.ProductService;
import com.app.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {

    private final SecurityService securityService;
    private final CartService cartService;
    private final ProductService productService;

    @GetMapping("/all")
    public String all(Model model) {
        /*model.addAttribute("carts", cartService.getAllUsersCarts(securityService.getLoggedInUser()));*/ // TODO: 31.12.2019
        return "carts/all";
    }

    @GetMapping("/one/{id}")
    public String one(@PathVariable Long id, Model model) {
        model.addAttribute("cart", cartService.getActiveCart(id));
        model.addAttribute("products", productService.getProductsOfCart(id));
        return "carts/one";
    }
}