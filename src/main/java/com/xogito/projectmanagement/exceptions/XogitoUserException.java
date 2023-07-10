package com.xogito.projectmanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class XogitoUserException extends RuntimeException {
    private String errorCode;
    public XogitoUserException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public XogitoUserException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public XogitoUserException(String message) {
        super(message);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getDescription () {
        return errorCode + " - " + getMessage();
    }
}