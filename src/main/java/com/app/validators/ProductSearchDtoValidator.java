package com.app.validators;

import com.app.Utilities.CustomRegex;
import com.app.dto.ProductSearchDTO;
import com.app.exceptions.AppException;
import com.app.model.InfoCodes;
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
        final String USER_INPUT_REGEX = CustomRegex.TEXT_WITH_DIGITS_REGEX;

        try {
            if (!productSearchDTO.getUserInput().matches(USER_INPUT_REGEX)) {
                errors.rejectValue("userInput", "NIEPRAWIDŁOWA WARTOŚĆ. DOZWOLONE SĄ TYLKO LITERY.");
            }
            if (productSearchDTO.getUserInput().length() > 50) {
                errors.rejectValue("userInput", "NIEPRAWIDŁOWA WARTOŚĆ. WPROWADZONY TEKST JEST ZA DŁUGI.");
            }
        } catch (Exception e) {
            throw new AppException(InfoCodes.VALIDATION, "ProductSearchDto");
        }
    }
}