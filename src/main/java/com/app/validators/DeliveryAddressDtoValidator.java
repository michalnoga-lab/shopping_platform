package com.app.validators;

import com.app.dto.DeliveryAddressDTO;
import com.app.exceptions.AppException;
import com.app.exceptions.ExceptionCodes;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class DeliveryAddressDtoValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(DeliveryAddressDTO.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        final String STREET_REGEX = "[\\w\\s]*";
        final String PHONE_REGEX = "\\d+";

        try {
            DeliveryAddressDTO deliveryAddressDTO = (DeliveryAddressDTO) o;

            if (!deliveryAddressDTO.getStreet().matches(STREET_REGEX)) {
                errors.rejectValue("street", "NIEPRAWIDŁOWY ADRES");
            }
            if (!deliveryAddressDTO.getPhone().matches(PHONE_REGEX)) {
                errors.rejectValue("phone", "NIEPRAWIDŁOWY TELEFON");
            }
        } catch (Exception e) {
            throw new AppException(ExceptionCodes.VALIDATION, "DeliveryAddressDto validation");
        }
    }
}