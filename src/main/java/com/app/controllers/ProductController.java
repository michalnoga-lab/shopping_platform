package com.app.controllers;

import com.app.service.CompanyService;
import com.app.service.ProductService;
import com.app.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final SecurityService securityService;
    private final ProductService productService;
    private final CompanyService companyService;

    @GetMapping("/all")
    public String all(Model model) {
        model.addAttribute("products",
                productService.getProductsOfCompany(companyService.getCompanyOfUser(securityService.getLoggedInUser())));

        return "/products/all";
    }
}