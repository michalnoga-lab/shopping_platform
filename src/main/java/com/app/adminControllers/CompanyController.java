package com.app.adminControllers;

import com.app.dto.CompanyDTO;
import com.app.repository.CompanyRepository;
import com.app.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("admin/companies")
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("add")
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

    // TODO: 14.01.2020 edit delete
}