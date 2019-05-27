package com.agility.usermanagement.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidJwtAuthenticationException extends BaseCustomException {

    public InvalidJwtAuthenticationException(CustomError error) {
        super(error, HttpStatus.UNAUTHORIZED);
    }
}