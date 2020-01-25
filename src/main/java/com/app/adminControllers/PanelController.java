package com.app.adminControllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class PanelController {

    @GetMapping("panel")
    public String adminShowPanel() {
        return "admin/panel";
    }
}