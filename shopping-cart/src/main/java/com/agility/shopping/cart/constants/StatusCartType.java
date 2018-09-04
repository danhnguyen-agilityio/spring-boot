package com.agility.shopping.cart.constants;

/**
 * StatusCartType enum defines value of each status type
 */
public enum StatusCartType {
    INPROGRESS("INPROGRESS"),
    DONE("DONE");

    private String name;

    StatusCartType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
