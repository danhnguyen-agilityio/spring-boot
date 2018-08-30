package com.agility.spring.response;

import java.util.HashMap;
import java.util.Map;

/**
 * ApiError class describe info about code and message
 */
public class ApiError {
    int code;
    String message;
    Map<String, String> errors;

    public ApiError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ApiError(int code, String message, Map<String, String> errors) {
        this.code = code;
        this.message = message;
        this.errors = errors;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}
