package com.app.controllers;

import com.app.service.CartService;
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

    @GetMapping("/all")
    public String all(Model model) {
        model.addAttribute("carts", cartService.getAllUsersCarts(securityService.getLoggedInUser()));
        return "/carts/all";
    }

    @GetMapping("/one/{id}")
    public String one(@PathVariable Long id, Model model) {
        model.addAttribute("carts", ""); // TODO: 2019-09-30
        return "/carts/one"; // TODO: 2019-09-30
    }
}