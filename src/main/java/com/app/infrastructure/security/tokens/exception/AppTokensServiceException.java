package com.app.infrastructure.security.tokens.exception;

public class AppTokensServiceException extends RuntimeException {

    public AppTokensServiceException(String message) {
        super(message);
    }
}