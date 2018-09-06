package com.agility.shopping.cart.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles custom exception
 */
@ControllerAdvice
@Slf4j
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BaseCustomException.class)
    public ResponseEntity<ApiError> handleCustomException(BaseCustomException ex) {
        ApiError apiError = new ApiError(ex.getCode(), ex.getMessage());
        return new ResponseEntity<>(apiError, ex.getHttpStatus());
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstrainViolation(
        ConstraintViolationException ex) {

        List<String> errors = new ArrayList<>();
        for (ConstraintViolation violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " +
                violation.getPropertyPath() + ": " + violation.getMessage());
        }

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST.value(),
            ex.getLocalizedMessage(), errors);

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
}
