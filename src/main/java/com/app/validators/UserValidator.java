package com.app.validators;

import com.app.dto.UserDTO;
import com.app.exceptions.AppException;
import com.app.exceptions.ExceptionCodes;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UserDTO.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserDTO userDTO = (UserDTO) o;
        final String USER_NAME_SURNAME_REGEX = "[a-zA-z\\s-ąćęłńóśźżĄĆĘŁŃÓŚŹŻ]+";
        final String PASSWORD_REGEX = "[.]{8,50}";

        try {
            if (!userDTO.getName().matches(USER_NAME_SURNAME_REGEX)) {
                errors.rejectValue("name", "NIEPRAWIDŁOWA WARTOŚĆ. DOZWOLONE SĄ TYLKO LITERY.");
            }
        } catch (Exception e) {
            throw new AppException(ExceptionCodes.VALIDATION, "UserDto");
        }

    }
}