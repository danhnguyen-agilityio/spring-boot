package com.agility.handlerexception.restcontrolleradvice.exception;

import com.agility.handlerexception.restcontrolleradvice.model.CustomError;
import org.springframework.http.HttpStatus;

public class BaseCustomException extends RuntimeException {
    private int code;
    private HttpStatus httpStatus;

    public BaseCustomException(CustomError customError, HttpStatus httpStatus) {
        super(customError.message());
        this.code = customError.code();
        this.httpStatus = httpStatus;
    }

    public int getCode() {
        return code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
