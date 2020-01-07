package com.app.controllers;

import com.app.dto.ProductSearchDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {

    @GetMapping("/products")
    public String searchGET(Model model) {
        model.addAttribute("productSearch", new ProductSearchDTO());
        model.addAttribute("errors", new HashMap<>());
        return "products/search";
    }

    @PostMapping("/products")
    public String searchPOST(@Valid @ModelAttribute ProductSearchDTO productSearchDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = bindingResult.getFieldErrors()
                    .stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getCode));
            model.addAttribute("productSearch", new ProductSearchDTO());
            model.addAttribute("errors", errors);
            return "products/search";
        }
        model.addAttribute("productSearch", productSearchDTO);
        return "redirect:/products/all";
    }
}