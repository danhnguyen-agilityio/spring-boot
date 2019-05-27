package com.agility.resourceserver.exceptions;

import org.springframework.http.HttpStatus;

public class InternalServerException extends BaseCustomException {

    public InternalServerException(CustomError error) {
        super(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
