package com.app.domain.configs.validator;

/**
 * ValidatorException class
 * Provides exception handling for validation
 */
public class ValidatorException extends RuntimeException {
    public ValidatorException(String message) {
        super(message);
    }
}
