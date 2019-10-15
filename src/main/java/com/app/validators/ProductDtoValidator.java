package com.app.validators;

import com.app.dto.ProductDTO;
import com.app.exceptions.AppException;
import com.app.exceptions.ExceptionCodes;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ProductDtoValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(ProductDTO.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ProductDTO productDTO = (ProductDTO) o;
        final String QUANTITY_REGEX = "[\\d]*";

        try {
            if (productDTO.getQuantity() <= 0 || !productDTO.getQuantity().toString().matches(QUANTITY_REGEX)) {
                errors.rejectValue("quantity", "NIEPRAWIDŁOWA ILOŚĆ");
            }

        } catch (Exception e) {
            throw new AppException(ExceptionCodes.VALIDATION, "ProductDTO validation");
        }
    }
}