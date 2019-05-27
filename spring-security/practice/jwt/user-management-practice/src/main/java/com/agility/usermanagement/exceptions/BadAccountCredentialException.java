package com.agility.usermanagement.exceptions;

import org.springframework.http.HttpStatus;

/**
 * BadAccountCredentialException class define bad account credential exception
 */
public class BadAccountCredentialException extends BaseCustomException {

    public BadAccountCredentialException(CustomError error) {
        super(error, HttpStatus.UNAUTHORIZED);
    }
}
