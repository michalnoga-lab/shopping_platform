package com.app.controllersWebAdmin;

import com.app.dto.UserDTO;
import com.app.service.UserService;
import com.app.validators.UserDtoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("admin/users")
public class AdminUserController {

    private final UserService userService;

    private final UserDtoValidator userDtoValidator;

    @InitBinder
    private void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.setValidator(userDtoValidator);
    }

    @GetMapping("add")
    public String addGET(Model model) {
        model.addAttribute("user", new UserDTO());
        model.addAttribute("errors", new HashMap<>());
        return "admin/users/add";
    }

    @PostMapping("add")
    public String addPOST(@Valid @ModelAttribute UserDTO userDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = bindingResult
                    .getFieldErrors()
                    .stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getCode));
            model.addAttribute("user", userDTO);
            model.addAttribute("errors", errors);
            return "admin/users/add";
        }
        userService.addUser(userDTO);
        return "redirect:/admin/users/added";
    }

    @GetMapping("all")
    public String getAll(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin/users/all";
    }

    @PostMapping("one/{id}")
    public String one(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "/admin/users/one";
    }

    @PostMapping("disable/{id}")
    public String disable(@PathVariable Long id) {
        userService.disableEnable(id, false);
        return "redirect:/admin/users/all";
    }

    @PostMapping("enable/{id}")
    public String enable(@PathVariable Long id) {
        userService.disableEnable(id, true);
        return "redirect:/admin/users/all";
    }

    // TODO: 14.01.2020 edit method
}