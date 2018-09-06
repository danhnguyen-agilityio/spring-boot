package com.agility.shopping.cart.exceptions;


import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;

/**
 * This class implement a simple structure for sending errors
 */
@AllArgsConstructor
public class ApiError {

    private int code;
    private String message;
    private List<String> errors;

    public ApiError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ApiError(int code, String message, String error) {
        this.code = code;
        this.message = message;
        this.errors = Arrays.asList(error);
    }
}
