package com.agility.spring.exceptions;

/**
 * CustomError enum define code error
 */
public enum CustomError {

    NOT_FOUND(400, "Resource not found"),
    NOT_FOUND_USER(401, "User is not found"),
    BAD_REQUEST(402, "Invalid request"),
    EXIST_EMAIL(403, "Email exist in system");

    private int code;
    private String message;


    CustomError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }
}
