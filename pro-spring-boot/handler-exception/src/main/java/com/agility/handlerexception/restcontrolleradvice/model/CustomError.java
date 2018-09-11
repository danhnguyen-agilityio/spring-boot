package com.agility.handlerexception.restcontrolleradvice.model;

public enum CustomError {
    USER_NOT_FOUND(401, "User not found"),
    BAD_REQUEST(400, "Invalid request");

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
