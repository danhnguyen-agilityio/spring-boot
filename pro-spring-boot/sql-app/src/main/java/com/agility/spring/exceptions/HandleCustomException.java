package com.agility.spring.exceptions;

import com.agility.spring.response.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * HandleCustomException class handles custom exception
 */
@ControllerAdvice
public class HandleCustomException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BaseCustomException.class)
    public ResponseEntity handleIOException(BaseCustomException e) {
        ApiError apiError = new ApiError(e.getCode(), e.getMessage());
        return new ResponseEntity<>(apiError, e.getHttpStatus());
    }

    /**
     * Handle MethodArgumentNotValidException thrown when request html
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errorMessages = new ArrayList<>();

        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            errorMessages.add(error.getDefaultMessage());
        }

        ApiError apiError = new ApiError(status.value(), String.join(", ", errorMessages));
        return new ResponseEntity<>(apiError, status);
    }
}
