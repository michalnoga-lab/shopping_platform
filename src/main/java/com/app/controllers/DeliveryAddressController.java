package com.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/delivery")
public class DeliveryAddressController {

    @GetMapping("/all")
    public String all() {
        return "";
    }
}