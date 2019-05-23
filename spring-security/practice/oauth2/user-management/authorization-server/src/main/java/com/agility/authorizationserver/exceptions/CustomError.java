package com.agility.authorizationserver.exceptions;

/**
 * CustomError enum define code and message error
 */
public enum CustomError {

    METHOD_ARGUMENT_NOT_VALID(400),
    MISSING_REQUEST_PARAM(401),
    METHOD_ARGUMENT_TYPE_MISMATCH(402),
    NO_HANDLER_FOUND(403),
    METHOD_NOT_ALLOWED(404),
    USERNAME_ALREADY_EXISTS(411, "Username already exists"),
    UNSUPPORTED_MEDIA_TYPE(405);

    private int code;
    private String message;

    /**
     * Constructor with given code
     */
    CustomError(int code) {
        this.code = code;
    }

    /**
     * Constructor with given code and message
     */
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
