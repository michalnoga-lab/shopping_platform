package com.app.validators;

import com.app.dto.ProductSearchDTO;
import com.app.exceptions.AppException;
import com.app.exceptions.ExceptionCodes;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ProductSearchDtoValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(ProductSearchDTO.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ProductSearchDTO productSearchDTO = (ProductSearchDTO) o;
        final String USER_INPUT_REGEX = "[\\w\\s]+";

        try {
            if (!productSearchDTO.getUserNameInput().matches(USER_INPUT_REGEX)) {
                errors.rejectValue("nameInput", "NIEPRAWIDŁOWA WARTOŚĆ. DOZWOLONE SĄ TYLKO LITERY.");
            }
            if (!productSearchDTO.getUserDescriptionInput().matches(USER_INPUT_REGEX)) {
                errors.rejectValue("descriptionInput", "NIEPRAWIDŁOWA WARTOŚĆ. DOZWOLONE SĄ TYLKO LITERY.");
            }
            if (productSearchDTO.getUserNameInput().length() > 50) {
                errors.rejectValue("nameInput", "NIEPRAWIDŁOWA WARTOŚĆ. WPROWADZONY TEKST JEST ZA DŁUGI.");
            }
            if (productSearchDTO.getUserDescriptionInput().length() > 50) {
                errors.rejectValue("descriptionInput", "NIEPRAWIDŁOWA WARTOŚĆ. WPROWADZONY TEKST JEST ZA DŁUGI.");
            }
        } catch (Exception e) {
            throw new AppException(ExceptionCodes.VALIDATION, "ProductSearchDto - validation");
        }
    }
}