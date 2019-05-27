package com.agility.spring.exceptions;

import com.agility.spring.response.ApiError;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HandleCustomException class handles custom exception
 */
@ControllerAdvice
@Slf4j
public class HandleCustomException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BaseCustomException.class)
    public ResponseEntity handleIOException(BaseCustomException e) {
        log.debug("BaseCustomException");
        ApiError apiError = new ApiError(e.getCode(), e.getMessage());
        return new ResponseEntity<>(apiError, e.getHttpStatus());
    }

    /**
     * Handle MethodArgumentNotValidException thrown when request html
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        val errorMessages = new HashMap<String, String>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errorMessages.put(error.getField(), error.getDefaultMessage());
        }

        ApiError apiError = new ApiError(CustomError.BAD_REQUEST.code(),
            CustomError.BAD_REQUEST.message(),
            errorMessages);
        return new ResponseEntity<>(apiError, status);
    }

    /**
     * Handle exception when not have any method handle thrown exception
     */
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ApiError> handleAllExceptions(Exception ex) {
//        ApiError response = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
//            ex.getMessage());
//        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
