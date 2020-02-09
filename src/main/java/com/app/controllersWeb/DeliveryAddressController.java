package com.app.controllersWeb;

import com.app.dto.DeliveryAddressDTO;
import com.app.service.CartService;
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
    private final CartService cartService;

    private final DeliveryAddressDtoValidator deliveryAddressDtoValidator;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.setValidator(deliveryAddressDtoValidator);
    }

    @GetMapping("/all")
    public String all(@ModelAttribute DeliveryAddressDTO deliveryAddressDTO, Model model) {
        List<DeliveryAddressDTO> addresses = deliveryAddressService.findAll(securityService.getLoggedInUserId());

        if (addresses.size() == 0) {
            model.addAttribute("address", new DeliveryAddressDTO());
            model.addAttribute("errors", new HashMap<>());
            return "deliveryAddress/add";
        }
        model.addAttribute("openCart", cartService.userHasOpenCart(securityService.getLoggedInUserId()));
        model.addAttribute("addresses", addresses);
        return "deliveryAddress/all";
    }

    @GetMapping("/add")
    public String addGET(Model model) {
        model.addAttribute("address", new DeliveryAddressDTO());
        model.addAttribute("errors", new HashMap<>());
        return "deliveryAddress/add";
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

    @PostMapping("/remove/{id}")
    public String remove(@PathVariable Long id) {
        deliveryAddressService.hide(id);
        return "redirect:/deliveryAddress/all";
    }

    @PostMapping("pick/{id}")
    public String pickPOST(@PathVariable Long id) {
        cartService.setAddressToCart(id, securityService.getLoggedInUserId());
        return "deliveryAddress/picked";
    }
}