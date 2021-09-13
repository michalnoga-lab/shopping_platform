package com.app.infrastructure.security.tokens.exception;

/**
 * Tokens service exception class
 */
public class AppTokensServiceException extends RuntimeException {

    public AppTokensServiceException(String message) {
        super(message);
    }
}