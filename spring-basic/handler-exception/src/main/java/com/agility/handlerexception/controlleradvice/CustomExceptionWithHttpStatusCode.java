package com.agility.handlerexception.controlleradvice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not found controlleradvice")
public class CustomExceptionWithHttpStatusCode extends RuntimeException {
}
