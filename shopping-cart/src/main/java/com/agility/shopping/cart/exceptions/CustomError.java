package com.agility.shopping.cart.exceptions;

/**
 * CustomError enum define code and message error
 */
public enum CustomError {

    METHOD_ARGUMENT_NOT_VALID(400),
    MISSING_REQUEST_PARAM(401),
    METHOD_ARGUMENT_TYPE_MISMATCH(402),
    NO_HANDLER_FOUND(403),
    METHOD_NOT_ALLOWED(404),
    UNSUPPORTED_MEDIA_TYPE(405),
    PRODUCT_EXIST(410, "Product is already existed"),
    PRODUCT_NOT_FOUND(411, "Product not found"),
    USER_NOT_FOUND(420, "User not found"),
    SHOPPING_CART_EXIST(430, "Shopping cart is already existed"),
    SHOPPING_CART_NOT_FOUND(431, "Shopping cart not found"),
    SHOPPING_CART_DONE(433, "Shopping cart is already done"),
    SHOPPING_CART_EMPTY(434, "Shopping cart empty"),
    CART_ITEM_NOT_FOUND(440, "Cart item not found");


    private int code;
    private String message;

    /**
     * Constructor with given code
     */
    CustomError(int code) {
        this.code = code;
    }

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
