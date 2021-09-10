package com.app.infrastructure.security.exceptions;

public class AppSecurityException extends RuntimeException {

    public AppSecurityException(String message) {
        super(message);
    }
}