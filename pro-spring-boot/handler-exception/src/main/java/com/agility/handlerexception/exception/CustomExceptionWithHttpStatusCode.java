package com.agility.handlerexception.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not found exception")
public class CustomExceptionWithHttpStatusCode extends RuntimeException {
}
