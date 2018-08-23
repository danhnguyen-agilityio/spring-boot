package com.agility.spring.response;

public class ApiError {
  int code;
  String message;

  public ApiError(int code, String message) {
    this.code = code;
    this.message = message;
  }
}
