package com.app.controllersAdminRest;

import com.app.dto.CompanyDTO;
import com.app.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/admin/companies")
public class AdminCompanyRestController {

    private final CompanyService companyService;

    @PostMapping("/add")
    public ResponseEntity<CompanyDTO> add(@RequestBody CompanyDTO companyDTO) {
        return new ResponseEntity<>(companyService.add(companyDTO), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CompanyDTO>> adminAll() {
        return new ResponseEntity<>(companyService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/one/{id}")
    public ResponseEntity<CompanyDTO> one(@PathVariable Long id) {
        return new ResponseEntity<>(companyService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<CompanyDTO> edit(@PathVariable Long id) {
        return new ResponseEntity<>(companyService.edit(id), HttpStatus.OK);
    }

    @PostMapping("/enable/{id}")
    public ResponseEntity<CompanyDTO> enable(@PathVariable Long id) {
        return new ResponseEntity<>(companyService.disableEnable(id, true), HttpStatus.OK);
    }

    @PostMapping("/disable/{id}")
    public ResponseEntity<CompanyDTO> disable(@PathVariable Long id) {
        return new ResponseEntity<>(companyService.disableEnable(id, false), HttpStatus.OK);
    }
}