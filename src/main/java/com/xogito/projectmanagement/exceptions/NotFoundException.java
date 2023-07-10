package com.xogito.projectmanagement.exceptions;

public class NotFoundException extends RuntimeException {

    private String errorCode;
    public NotFoundException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public NotFoundException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public NotFoundException(String message) {
        super(message);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getDescription () {
        return errorCode + " - " + getMessage();
    }
}