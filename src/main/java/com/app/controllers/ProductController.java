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

    @GetMapping("/buy/{id}")
    public String buyGET(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getOneProduct(id));
        model.addAttribute("errors", new HashMap<>());

        System.out.println("--------------------- 8 -------------------");
        System.out.println(productService.getOneProduct(id));

        return "products/buy";
    }

    @PostMapping("/buy")
    public String buyPOST(@Valid @ModelAttribute ProductDTO productDTO, @PathVariable Long id,
                          BindingResult bindingResult, Model model) {

        // TODO: 2019-10-23
        System.out.println("------------------ 7 -------------------");

        //System.out.println("ID= " + id);

        /*model.addAttribute("product", productDTO);*/


        if (bindingResult.hasErrors()) {

            System.out.println("------------------ 7 -------------------");
            System.out.println("buy method has errors !!!");

            Map<String, String> errors = bindingResult.getFieldErrors()
                    .stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getCode));
            model.addAttribute("product", new ProductDTO());
            model.addAttribute("errors", errors);
            return "products/buy";
        }
        model.addAttribute("product", productDTO);
        System.out.println("przekazane ID: " + id);
        System.out.println(productDTO); // TODO: 03.01.2020 id nie przechodzi

        cartService.addProductToCart(ProductDTO.builder().id(5L).name("NAME").build(), 3L); // TODO: 31.12.2019 remove
        //return "redirect:/products/added"; // TODO: 02.01.2020 page product added to cart / display cart
        return "redirect:/products/all"; // TODO: 03.01.2020 temporary
    }
}