package com.app.infrastructure.security.exceptions;

/**
 * Security exception class
 */
public class AppSecurityException extends RuntimeException {

    public AppSecurityException(String message) {
        super(message);
    }
}