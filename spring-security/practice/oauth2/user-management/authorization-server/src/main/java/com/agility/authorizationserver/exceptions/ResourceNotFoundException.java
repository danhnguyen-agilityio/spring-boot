package com.agility.authorizationserver.exceptions;

import org.springframework.http.HttpStatus;

/**
 * ResourceNotFoundException class define resource not found exception
 */
public class ResourceNotFoundException extends BaseCustomException {

    public ResourceNotFoundException(CustomError error) {
        super(error, HttpStatus.NOT_FOUND);
    }
}

