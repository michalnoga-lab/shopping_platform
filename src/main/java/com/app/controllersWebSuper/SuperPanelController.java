package com.app.controllersWebSuper;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("super")
public class SuperPanelController {

    @GetMapping("/panel")
    public String showSuperPanel() {
        return "super/panel";
    }
}