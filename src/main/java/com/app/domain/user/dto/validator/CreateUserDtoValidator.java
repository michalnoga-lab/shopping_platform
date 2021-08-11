package com.app.domain.user.dto.validator;

import com.app.domain.configs.validator.Validator;
import com.app.domain.user.dto.CreateUserDto;

import java.util.HashMap;
import java.util.Map;

import static com.app.domain.configs.regex.Regex.EMAIL_REGEX;
import static com.app.domain.configs.regex.Regex.TEXT_REGEX;

/**
 * CreateUserDtoValidator class
 * Validates values given by user at registration time
 *
 * @see CreateUserDto
 */
public class CreateUserDtoValidator implements Validator<CreateUserDto> {

    @Override
    public Map<String, String> validate(CreateUserDto createUserDto) {

        var errors = new HashMap<String, String>();

        if (createUserDto == null) {
            errors.put("create user dto", "is null");
        }

        var name = createUserDto.getName();
        if (hasIncorrectName(name)) {
            errors.put("create user dto", "has incorrect name");
        }

        var surname = createUserDto.getSurname();
        if (hasIncorrectSurname(surname)) {
            errors.put("create user dto", "has incorrect surname");
        }

        var email = createUserDto.getEmail();
        if (hasIncorrectEmail(email)) {
            errors.put("create user dto", "has incorrect email");
        }

        var password = createUserDto.getPassword();
        var passwordConfirmation = createUserDto.getPasswordConfirmation();
        if (hasIncorrectPassword(password, passwordConfirmation)) {
            errors.put("create user dto", "has incorrect password");
        }

        return errors;
    }

    private boolean hasIncorrectName(String name) {
        return name == null || !name.matches(TEXT_REGEX) || name.length() > 150;
    }

    private boolean hasIncorrectSurname(String surname) {
        return surname == null || !surname.matches(TEXT_REGEX) || surname.length() > 150;
    }

    private boolean hasIncorrectEmail(String email) {
        return email == null || !email.matches(EMAIL_REGEX) || email.length() > 150;
    }

    private boolean hasIncorrectPassword(String password, String passwordConfirmation) {
        return password == null || password.length() > 256 || passwordConfirmation == null || passwordConfirmation.length() < 8
                || passwordConfirmation.length() > 256 || !password.equals(passwordConfirmation);
    }
}