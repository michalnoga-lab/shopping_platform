package com.app.controllers;

import com.app.dto.DeliveryAddressDTO;
import com.app.service.DeliveryAddressService;
import com.app.service.SecurityService;
import com.app.validators.DeliveryAddressDtoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/deliveryAddress")
public class DeliveryAddressController {

    private final DeliveryAddressService deliveryAddressService;
    private final SecurityService securityService;

    private final DeliveryAddressDtoValidator deliveryAddressDtoValidator;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.setValidator(deliveryAddressDtoValidator);
    }

    @GetMapping("/all")
    public String all(@ModelAttribute DeliveryAddressDTO deliveryAddressDTO, Model model) {
        List<DeliveryAddressDTO> addresses = deliveryAddressService.getAll(securityService.getLoggedInUserId());

        if (addresses.size() == 0) {
            model.addAttribute("address", new DeliveryAddressDTO());
            model.addAttribute("errors", new HashMap<>());
            return "deliveryAddress/add";
        }
        model.addAttribute("addresses", addresses);
        return "/deliveryAddress/all";
    }

    @GetMapping("/add")
    public String addGET(Model model) {
        model.addAttribute("address", new DeliveryAddressDTO());
        model.addAttribute("errors", new HashMap<>());
        return "/deliveryAddress/add";
    }

    @PostMapping("/add")
    public String addPOST(@Valid @ModelAttribute DeliveryAddressDTO deliveryAddressDTO, BindingResult bindingResult,
                          Model model) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = bindingResult.getFieldErrors()
                    .stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getCode));
            model.addAttribute("address", new DeliveryAddressDTO());
            model.addAttribute("errors", errors);
            return "deliveryAddress/add";
        }
        model.addAttribute("address", deliveryAddressDTO);
        deliveryAddressService.add(deliveryAddressDTO, securityService.getLoggedInUserId());
        return "redirect:/deliveryAddress/all";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam Long id) {
        deliveryAddressService.remove(id);
        return "redirect:/deliveryAddress/all";
    }
}