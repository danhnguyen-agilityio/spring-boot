package com.agility.usermanagement.exceptions;

import org.springframework.http.HttpStatus;

/**
 * BadRequestException class define bad request exception
 */
public class BadRequestException extends BaseCustomException {
    public BadRequestException(CustomError error) {
        super(error, HttpStatus.BAD_REQUEST);
    }
}
