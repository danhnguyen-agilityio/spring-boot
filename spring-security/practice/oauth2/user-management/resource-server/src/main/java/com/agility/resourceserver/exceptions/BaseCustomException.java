package com.agility.resourceserver.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * BaseCustomException class
 */
@Getter
public class BaseCustomException extends RuntimeException {

    private int code;
    private HttpStatus httpStatus;

    public BaseCustomException(CustomError error, HttpStatus httpStatus) {
        super(error.message());
        this.code = error.code();
        this.httpStatus = httpStatus;
    }
}
