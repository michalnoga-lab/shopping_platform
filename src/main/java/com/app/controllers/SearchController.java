package com.app.controllers;

import com.app.dto.ProductSearchDTO;
import com.app.validators.ProductSearchDtoValidator;
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
@RequestMapping("/search")
public class SearchController {

    private final ProductSearchDtoValidator productSearchDtoValidator;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.setValidator(productSearchDtoValidator);
    }

    @GetMapping("/products")
    public String searchGET(Model model) {
        model.addAttribute("productSearch", new ProductSearchDTO());
        model.addAttribute("errors", new HashMap<>());
        return "search/products";
    }

    @PostMapping("/products")
    public String searchPOST(@Valid @ModelAttribute ProductSearchDTO productSearchDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = bindingResult.getFieldErrors()
                    .stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getCode));
            model.addAttribute("productSearch", new ProductSearchDTO());
            model.addAttribute("errors", errors);
            return "search/products";
        }
        model.addAttribute("productSearch", productSearchDTO);
        return "redirect:/products/all";
    }
}