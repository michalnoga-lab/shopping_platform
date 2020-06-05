package com.app.validators;

import com.app.dto.CompanySearchDTO;
import com.app.exceptions.AppException;
import com.app.model.InfoCodes;
import com.app.model.Company;
import com.app.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CompanySearchDtoValidator implements Validator {

    private final CompanyRepository companyRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(CompanySearchDTO.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        final String USER_ID_INPUT_REGEX = "(\\d+)";

        try {
            CompanySearchDTO companySearchDTO = (CompanySearchDTO) o;

            Optional<Company> companyOptional = companyRepository.findById(companySearchDTO.getUserIdInput());
            if (companyOptional.isEmpty() || companyOptional.get().toString().matches(USER_ID_INPUT_REGEX)) {
                errors.rejectValue("userIdInput", "NIEPRAWIDŁOWA WARTOŚĆ. DOZWOLONE SĄ TYLKO CYFRY.");
            }
        } catch (Exception e) {
            throw new AppException(InfoCodes.VALIDATION, "CompanySearchDTO");
        }
    }
}