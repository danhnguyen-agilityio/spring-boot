package com.agility.spring.exceptions;

import org.springframework.http.HttpStatus;

/**
 * BaseCustomException class extends RuntimeException
 */
public class BaseCustomException extends RuntimeException {

  private int code;

  private HttpStatus httpStatus;

  public BaseCustomException(CustomError error, HttpStatus httpStatus) {
    super(error.message());

    this.code = error.code();
    this.httpStatus = httpStatus;
  }

  public int getCode() {
    return code;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }
}
