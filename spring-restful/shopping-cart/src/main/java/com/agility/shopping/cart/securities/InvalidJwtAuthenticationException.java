package com.agility.shopping.cart.securities;

import com.agility.shopping.cart.exceptions.BaseCustomException;
import com.agility.shopping.cart.exceptions.CustomError;
import org.springframework.http.HttpStatus;

/**
 * InvalidJwtAuthenticationException class define invalid jwt authentication exception
 */
public class InvalidJwtAuthenticationException extends BaseCustomException {

    public InvalidJwtAuthenticationException(CustomError error) {
        super(error, HttpStatus.BAD_REQUEST);
    }
}
