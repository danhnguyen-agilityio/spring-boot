package com.agility.usermanagement.exceptions;

/**
 * CustomError enum define code and message error
 */
public enum CustomError {

    METHOD_ARGUMENT_NOT_VALID(400),
    MISSING_REQUEST_PARAM(401),
    METHOD_ARGUMENT_TYPE_MISMATCH(402),
    NO_HANDLER_FOUND(403),
    METHOD_NOT_ALLOWED(404),
    EMAIL_ALREADY_EXISTS(411, "Email already exists"),
    UNSUPPORTED_MEDIA_TYPE(405),
    USER_NOT_FOUND(410, "User not found"),
    BAD_CREDENTIALS(451, "Invalid username/password supplied"),
    EXPIRED_TOKEN(452, "Token has expired"),
    INVALID_TOKEN(453, "Token is invalid"),
    INVALID_ROLE_NAME(460, "Role name is invalid, only accept value (USER, MANAGER, ADMIN)"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

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
