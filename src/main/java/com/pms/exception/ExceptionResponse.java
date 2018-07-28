package com.pms.exception;

import java.util.ArrayList;
import java.util.List;

public class ExceptionResponse {

    private String errorCode;
    private String errorMessage;
    private List<String> errors;

    public ExceptionResponse() {
        errors = new ArrayList<String>();
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public void addError(String str) {
        errors.add(str);
    }
}

