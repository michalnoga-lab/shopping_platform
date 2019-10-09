package com.app.controllers;

import com.app.dto.DeliveryAddressDTO;
import com.app.service.DeliveryAddressService;
import com.app.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller()
@RequiredArgsConstructor
@RequestMapping("/deliveryAddress")
public class DeliveryAddressController {

    private final DeliveryAddressService deliveryAddressService;
    private final SecurityService securityService;

    @GetMapping("/all")
    public String all(@ModelAttribute DeliveryAddressDTO deliveryAddressDTO, Model model) {
        List<DeliveryAddressDTO> addresses = deliveryAddressService.getAll(securityService.getLoggedInUser());

        if (addresses.size() == 0) {
            return "/deliveryAddress/add";
        }
        model.addAttribute("addresses", addresses);
        return "/deliveryAddress/all";
    }

    @GetMapping("/add")
    public String addGET(Model model) {
        model.addAttribute("deliveryAddress", new DeliveryAddressDTO());
        return "/deliveryAddress/add";
    }

    @PostMapping("/add")
    public String addPOST(@ModelAttribute DeliveryAddressDTO deliveryAddressDTO, Model model) {
        model.addAttribute("address", new DeliveryAddressDTO());
        deliveryAddressService.add(deliveryAddressDTO, securityService.getLoggedInUser());
        return "/deliveryAddress/all";
    }
}