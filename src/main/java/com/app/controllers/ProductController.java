package com.app.controllers;

import com.app.dto.ProductDTO;
import com.app.service.CartService;
import com.app.service.CompanyService;
import com.app.service.ProductService;
import com.app.service.SecurityService;
import com.app.validators.ProductDtoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final SecurityService securityService;
    private final ProductService productService;
    private final CompanyService companyService;
    private final CartService cartService;

    private final ProductDtoValidator productDtoValidator;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.setValidator(productDtoValidator);
    }

    @GetMapping("/all")
    public String all(Model model) {
        model.addAttribute("products", productService.getProductsOfCompany(
                companyService.getCompanyOfUser(securityService.getLoggedInUser())));
        return "products/all";
    }

    @GetMapping("/one/{id}")
    public String one(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getOneProduct(id));

        // TODO: 2019-10-23
        System.out.println("------------ 6 ---------------");
        System.out.println(productService.getOneProduct(id));


        model.addAttribute("errors", new HashMap<>());
        return "products/one";
    }

    @GetMapping("/buy")
    public String buyGET(Model model) {
        model.addAttribute("product", new ProductDTO());
        model.addAttribute("errors", new HashMap<>());
        return "products/added";
    }

    @PostMapping("/buy")
    public String buyPOST(@Valid @ModelAttribute ProductDTO productDTO,
                          BindingResult bindingResult, Model model) {

        // TODO: 2019-10-23
        System.out.println("------------------ 7 -------------------");
        System.out.println(productDTO);

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = bindingResult.getFieldErrors()
                    .stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getCode));
            model.addAttribute("product", new ProductDTO());
            model.addAttribute("errors", errors);
            return "products/buy";
        }
        cartService.addProductToCart(productDTO, securityService.getLoggedInUser());
        return "redirect:/products/added";
    }
}