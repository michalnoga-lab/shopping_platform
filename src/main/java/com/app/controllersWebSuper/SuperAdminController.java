package com.app.controllersWebSuper;

import com.app.dto.UserDTO;
import com.app.model.Role;
import com.app.repository.UserRepository;
import com.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
@RequiredArgsConstructor
@RequestMapping("super/admins")
public class SuperAdminController {

    private final UserService userService;

    @GetMapping("add")
    public String addGET(Model model) {
        model.addAttribute("user", new UserDTO());
        model.addAttribute("errors", new HashMap<>());
        return "super/admins/add";
    }

    @PostMapping("add")
    public String addPOST(UserDTO userDTO, Model model) {
        userService.addUser(userDTO, Role.ADMIN);
        return "redirect:/admin/users/added";
    }
}