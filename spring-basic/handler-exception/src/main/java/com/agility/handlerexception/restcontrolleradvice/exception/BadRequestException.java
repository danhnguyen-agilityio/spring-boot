package com.agility.handlerexception.restcontrolleradvice.exception;

import com.agility.handlerexception.restcontrolleradvice.model.CustomError;
import org.springframework.http.HttpStatus;

public class BadRequestException extends BaseCustomException {

    public BadRequestException(CustomError customError) {
        super(customError, HttpStatus.BAD_REQUEST);
    }
}
