package com.agility.handlerexception.restcontrolleradvice.exception;

import com.agility.handlerexception.restcontrolleradvice.model.CustomError;
import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseCustomException {

    public NotFoundException(CustomError customError) {
        super(customError, HttpStatus.NOT_FOUND);
    }

}
