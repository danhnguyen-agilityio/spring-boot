package com.agility.resourceserver.exceptions;

import org.springframework.http.HttpStatus;

/**
 * ResourceAlreadyExistsException class define resource existed exception
 */
public class ResourceAlreadyExistsException extends BaseCustomException {

    public ResourceAlreadyExistsException(CustomError error) {
        super(error, HttpStatus.CONFLICT);
    }
}
