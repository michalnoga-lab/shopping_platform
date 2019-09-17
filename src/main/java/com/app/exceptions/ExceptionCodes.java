package com.app.exceptions;

public enum ExceptionCodes {

    CONTROLLERS("CONTROLLERS_EXCEPTION"),
    LAYOUT("LAYOUT_EXCEPTION"),
    MODEL("MODEL_EXCEPTION"),
    REPOSITORY("REPOSITORY_EXCEPTION"),
    VALIDATION("VALIDATION_EXCEPTION"),
    SECURITY("SECURITY_EXCEPTION"),
    SERVICE("SERVICE_EXCEPTION"),
    OTHER("OTHER_MINOR_EXCEPTIONS");

    private String exceptionCode;

    ExceptionCodes(String exceptionCode) {
        this.exceptionCode = exceptionCode;
    }
}