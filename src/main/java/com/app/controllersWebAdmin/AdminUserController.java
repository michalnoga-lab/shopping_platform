package com.app.controllersWebAdmin;

import com.app.dto.UserDTO;
import com.app.model.Role;
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
        userService.addUser(userDTO, Role.USER);
        return "redirect:/admin/users/added";
    }

    @GetMapping("added")
    public String added() {
        return "admin/users/added";
    }

    @GetMapping("all")
    public String getAll(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin/users/all";
    }

    @GetMapping("one/{id}")
    public String oneGET(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "admin/users/one";
    }

    @PostMapping("one/{id}")
    public String onePOST(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "admin/users/one";
    }

    @PostMapping("disable/{id}")
    public String disable(@PathVariable Long id) {
        userService.disableEnable(id, false);
        return "redirect:/admin/users/one/" + id;
    }

    @PostMapping("enable/{id}")
    public String enable(@PathVariable Long id) {
        userService.disableEnable(id, true);
        return "redirect:/admin/users/one/" + id;
    }

    @PostMapping("edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "admin/users/edit";
    }

    @PostMapping("edit")
    public String edit(@ModelAttribute UserDTO userDTO) {
        return "redirect:/admin/users/one/" + userDTO.getId(); // TODO: 2020-02-16 id
    }
}