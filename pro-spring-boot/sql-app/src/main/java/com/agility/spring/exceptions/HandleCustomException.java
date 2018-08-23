package com.agility.spring.exceptions;

import com.agility.spring.response.ApiError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * HandleCustomException class handles custom exception
 */
@ControllerAdvice
public class HandleCustomException {

  @ExceptionHandler(BaseCustomException.class)
  public ResponseEntity handleIOException(BaseCustomException e){
    ApiError apiError = new ApiError(e.getCode(), e.getMessage());
    return new ResponseEntity<>(apiError, e.getHttpStatus());
  }
}
