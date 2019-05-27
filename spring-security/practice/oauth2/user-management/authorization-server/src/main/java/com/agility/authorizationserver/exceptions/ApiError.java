package com.agility.authorizationserver.exceptions;


import lombok.*;

import java.util.Arrays;
import java.util.List;

/**
 * This class implement a simple structure for sending errors
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
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
