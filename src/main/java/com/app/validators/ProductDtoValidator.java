package com.app.validators;

import com.app.dto.ProductDTO;
import com.app.exceptions.AppException;
import com.app.exceptions.ExceptionCodes;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class ProductDtoValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(ProductDTO.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ProductDTO productDTO = (ProductDTO) o;
        Pattern pattern = Pattern.compile("\\d+");

        try {
            if (productDTO.getQuantity() <= 0) {
                errors.rejectValue("quantity", "ILOŚĆ MUSI BYĆ WIEKSZ OD ZERA.");
            }
            if (productDTO.getQuantity().toString().length() > 8) {
                errors.rejectValue("quantity", "NIEPRAWIDŁOWA WARTOŚĆ. WPROWADZONA WARTOŚĆ JEST ZA DŁUGA.");
            }
            if (!pattern.matcher(productDTO.getQuantity().toString()).matches()) {
                errors.rejectValue("quantity", "DOZWOLONE SĄ TYLKO CYFRY.");
            }
        } catch (Exception e) {
            throw new AppException(ExceptionCodes.VALIDATION, "ProductDTO");
        }
    }
}