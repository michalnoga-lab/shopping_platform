package com.app.controllersWebAdmin;

import com.app.dto.CompanyDTO;
import com.app.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("admin/companies")
public class AdminCompanyController {

    private final CompanyService companyService;

    @GetMapping(value = "add")
    public String adminAddCompanyGET(Model model) {
        model.addAttribute("company", new CompanyDTO());
        model.addAttribute("errors", new HashMap<>());
        return "admin/companies/add";
    }

    @PostMapping("add")
    public String adminAddCompanyPOST(@ModelAttribute CompanyDTO companyDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = bindingResult
                    .getFieldErrors()
                    .stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getCode));
            model.addAttribute("company", companyDTO);
            model.addAttribute("errors", errors);
            return "admin/companies/add";
        }
        companyService.add(companyDTO);
        return "admin/companies/added";
    }

    @GetMapping("all")
    public String adminAll(Model model) {
        model.addAttribute("companies", companyService.findAll());
        return "admin/companies/all";
    }

    @PostMapping("one/{id}")
    public String one(@PathVariable Long id, Model model) {
        model.addAttribute("company", companyService.findById(id));
        return "admin/companies/one";
    }

    @PostMapping("edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("company", companyService.edit(id));
        return "redirect:/admin/companies/all"; // TODO: 2020-01-25 TODO: jak przekazać ID powrotu do strony ???
    }

    @PostMapping("enable/{id}")
    public String enable(@PathVariable Long id) {
        companyService.disableEnable(id, true);
        return "redirect:/admin/companies/all"; // TODO: 21.01.2020 jak przekazać ID powrotu do strony ???
    }

    @PostMapping("disable/{id}")
    public String disable(@PathVariable Long id) {
        companyService.disableEnable(id, false);
        return "redirect:/admin/companies/all";
    }
}