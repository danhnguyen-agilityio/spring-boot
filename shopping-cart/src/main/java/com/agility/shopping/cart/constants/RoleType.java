package com.agility.shopping.cart.constants;

/**
 * RoleType enum defines value of each role
 */
public enum RoleType {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private String name;

    RoleType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
