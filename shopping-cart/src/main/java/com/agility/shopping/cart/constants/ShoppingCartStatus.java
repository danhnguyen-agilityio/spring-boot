package com.agility.shopping.cart.constants;

/**
 * ShoppingCartStatus enum defines value of each status type
 */
public enum ShoppingCartStatus {
    EMPTY("EMPTY"),
    IN_PROGRESS("IN_PROGRESS"),
    DONE("DONE");

    private String name;

    ShoppingCartStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
