package com.app.controllers;

import com.app.dto.ProductDTO;
import com.app.exceptions.AppException;
import com.app.exceptions.ExceptionCodes;
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

@Controller
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {

    private final SecurityService securityService;
    private final CartService cartService;
    private final ProductService productService;

    @GetMapping("/all")
    public String all(Model model) {
        model.addAttribute("carts", cartService.getAllUsersCarts(securityService.getLoggedInUser()));
        return "carts/all";
    }

    @GetMapping("/one/{id}")
    public String one(@PathVariable Long id, Model model) {
        model.addAttribute("cart", cartService.getOneCart(id));
        model.addAttribute("products", productService.getProductsOfCart(id));
        return "carts/one";
    }
}