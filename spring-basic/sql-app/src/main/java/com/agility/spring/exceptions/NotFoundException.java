package com.agility.spring.exceptions;

import org.springframework.http.HttpStatus;

/**
 * NotFoundException class extends BaseCustomException class
 */
public class NotFoundException extends BaseCustomException {

    public NotFoundException(CustomError error) {
        super(error, HttpStatus.NOT_FOUND);
    }
}
