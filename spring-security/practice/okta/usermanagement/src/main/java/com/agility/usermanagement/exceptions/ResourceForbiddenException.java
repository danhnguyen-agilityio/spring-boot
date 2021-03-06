package com.agility.usermanagement.exceptions;

import org.springframework.http.HttpStatus;

/**
 * ResourceForbiddenException class define resource forbidden exception
 */
public class ResourceForbiddenException extends BaseCustomException {

    public ResourceForbiddenException(CustomError error) {
        super(error, HttpStatus.FORBIDDEN);
    }
}
