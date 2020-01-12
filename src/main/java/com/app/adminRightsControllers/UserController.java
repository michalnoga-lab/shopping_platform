package com.app.adminRightsControllers;

import com.app.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class UserController {

    @GetMapping("/add")
    public String addGET(Model model) {
        model.addAttribute("user", new UserDTO());
        model.addAttribute("errors", new HashMap<>());
        return "";
    }

    @PostMapping("/add")
    public String addPOST() {

        return "";
    }
}