package com.agility.spring.exceptions;

import org.springframework.http.HttpStatus;

/**
 * BadRequestException class extends BaseCustomException class
 */
public class BadRequestException extends BaseCustomException {

    public BadRequestException(CustomError error) {
        super(error, HttpStatus.BAD_REQUEST);
    }
}
