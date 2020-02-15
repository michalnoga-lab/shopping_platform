package com.app.validators;

import com.app.Utilities.CustomRegex;
import com.app.dto.UserDTO;
import com.app.exceptions.AppException;
import com.app.model.InfoCodes;
import com.app.model.User;
import com.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDtoValidator implements Validator {

    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UserDTO.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserDTO userDTO = (UserDTO) o;
        final String USER_NAME_SURNAME_REGEX = CustomRegex.TEXT_ONLY_REGEX;
        final String PASSWORD_REGEX = "(.){7,50}";

        try {
            Optional<User> userOptional = userRepository.findByLogin(userDTO.getLogin());
            if (!userDTO.getName().matches(USER_NAME_SURNAME_REGEX)) {
                errors.rejectValue("name", "NIEPRAWIDŁOWA WARTOŚĆ. DOZWOLONE SĄ TYLKO LITERY.");
            }
            if (!userDTO.getSurname().matches(USER_NAME_SURNAME_REGEX)) {
                errors.rejectValue("surname", "NIEPRAWIDŁOWA WARTOŚĆ. DOZWOLONE SĄ TYLKO LITERY.");
            }
            if (!userDTO.getPassword().matches(PASSWORD_REGEX)) {
                errors.rejectValue("password",
                        "MINIMALNA DŁUGOŚĆ HASŁA 8 ZNAKÓW. MAKSYMALNA 50 ZNAKÓW.\n" +
                                "HASŁO ORAZ JEGO POTWIERDZENIE MUSZĄ BYĆ TAKIE SAME.\nDOZWOLONE SĄ WSZYSTKIE ZNAKI.");
            }
            if (!userDTO.getPasswordConfirmation().matches(PASSWORD_REGEX) ||
                    !userDTO.getPassword().equals(userDTO.getPasswordConfirmation())) {
                errors.rejectValue("passwordConfirmation",
                        "MINIMALNA DŁUGOŚĆ HASŁA 8 ZNAKÓW. MAKSYMALNA 50 ZNAKÓW\n" +
                                "HASŁO ORAZ JEGO POTWIERDZENIE MUSZĄ BYĆ TAKIE SAME.\nDOZWOLONE SĄ WSZYSTKIE ZNAKI.");
            }
        } catch (Exception e) {
            throw new AppException(InfoCodes.VALIDATION, "UserDto");
        }
    }
}