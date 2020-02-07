package com.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/security")
public class SecurityController {

    @GetMapping("login")
    public String login() {
        return "security/login";
    }

    @GetMapping("logout")
    public String logout() {
        return "security/logout";
    }

    @GetMapping("failed")
    public String loginFailed() {
        return "security/failed";
    }

    @GetMapping("accessDenied")
    public String accessDenied() {
        return "security/accessDenied";
    }
}