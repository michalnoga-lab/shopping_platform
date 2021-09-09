package com.app.infrastructure.exceptions;

public class AppSecurityException extends RuntimeException {

    public AppSecurityException(String message) {
        super(message);
    }
}
