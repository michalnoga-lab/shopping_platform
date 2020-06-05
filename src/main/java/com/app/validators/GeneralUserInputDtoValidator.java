package com.app.validators;

import com.app.Utilities.CustomRegex;
import com.app.dto.GeneralUserInputDTO;
import com.app.exceptions.AppException;
import com.app.model.InfoCodes;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class GeneralUserInputDtoValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(GeneralUserInputDTO.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        final String USER_INPUT_REGEX = CustomRegex.TEXT_WITH_DIGITS_REGEX;

        try {
            GeneralUserInputDTO generalUserInputDTO = (GeneralUserInputDTO) o;

            if (!generalUserInputDTO.getUserInput().matches(USER_INPUT_REGEX) || generalUserInputDTO.getUserInput().length() > 50) {
                errors.rejectValue("userInput", "NIEPRAWIDŁOWA WARTOŚĆ. DOZWOLONE SĄ TYLKO LITERY I CYFRY.");
            }

        } catch (Exception e) {
            throw new AppException(InfoCodes.VALIDATION, "generalUserInputDto");
        }


    }
}