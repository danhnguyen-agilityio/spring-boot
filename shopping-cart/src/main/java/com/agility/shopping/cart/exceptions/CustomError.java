package com.agility.shopping.cart.exceptions;

import lombok.Getter;

/**
 * CustomError enum define code and message error
 */
public enum CustomError {

    PRODUCT_EXIST(410, "Product is already existed"),
    PRODUCT_NOT_FOUND(411, "Product not found"),
    USER_NOT_FOUND(420, "User not found"),
    SHOPPING_CART_EXIST(430, "Shopping cart is already existed");

    private int code;
    private String message;

    /**
     * Constructor with given code and message
     */
    CustomError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }
}
