package com.app.validators;

import com.app.dto.UserDTO;
import com.app.exceptions.AppException;
import com.app.exceptions.ExceptionCodes;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserDtoValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UserDTO.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserDTO userDTO = (UserDTO) o;
        final String USER_NAME_SURNAME_REGEX = "[a-zA-z\\s-ąćęłńóśźżĄĆĘŁŃÓŚŹŻ]+";
        final String PASSWORD_REGEX = "(.){8,50}";

        try {
            if (!userDTO.getLogin().matches(USER_NAME_SURNAME_REGEX)) { // TODO: 14.01.2020 check if login is unique
                errors.rejectValue("login", "NIEPRAWIDŁOWA WARTOŚĆ. DOZWOLONE SĄ TYLKO LITERY.");
            }
            if (!userDTO.getName().matches(USER_NAME_SURNAME_REGEX)) {
                errors.rejectValue("name", "NIEPRAWIDŁOWA WARTOŚĆ. DOZWOLONE SĄ TYLKO LITERY.");
            }
            if (!userDTO.getSurname().matches(USER_NAME_SURNAME_REGEX)) {
                errors.rejectValue("surname", "NIEPRAWIDŁOWA WARTOŚĆ. DOZWOLONE SĄ TYLKO LITERY.");
            }
            if (!userDTO.getPassword().matches(PASSWORD_REGEX)) { // TODO: 14.01.2020 check if passwords are the same
                errors.rejectValue("password",
                        "MINIMALNA DŁUGOŚĆ HASŁA 8 ZNAKÓW. MAKSYMALNA 50 ZNAKÓW.\n" +
                                "HASŁO ORAZ JEGO POTWIERDZENIE MUSZĄ BYĆ TAKIE SAME.\nDOZWOLONE SĄ WSZYSTKIE ZNAKI.");
            }
            if (!userDTO.getPasswordConfirmation().matches(PASSWORD_REGEX)) {
                errors.rejectValue("passwordConfirmation",
                        "MINIMALNA DŁUGOŚĆ HASŁA 8 ZNAKÓW. MAKSYMALNA 50 ZNAKÓW\n" +
                                "HASŁO ORAZ JEGO POTWIERDZENIE MUSZĄ BYĆ TAKIE SAME.\nDOZWOLONE SĄ WSZYSTKIE ZNAKI.");
            }
        } catch (Exception e) {
            throw new AppException(ExceptionCodes.VALIDATION, "UserDto");
        }
    }
}