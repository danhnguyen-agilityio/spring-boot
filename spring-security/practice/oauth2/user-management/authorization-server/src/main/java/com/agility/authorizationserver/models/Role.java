package com.agility.authorizationserver.models;

/**
 * Defines name role
 */
public enum Role {
    USER("ROLE_USER"),
    MANAGER("ROLE_MANAGER"),
    ADMIN("ROLE_ADMIN");

    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_MANAGER = "ROLE_MANAGER";

    private String name;

    Role(String name) {
        this.name = name;
    }

    /**
     * Get name of role, default spring security use prefix ROLE_
     *
     * @return Name of role with prefix ROLE_
     */
    public String getName() {
        return name;
    }

    /**
     * Get Role by name
     * @param name role name
     * @return Role enum
     */
    public static Role getRole(String name) {
        try {
            return Role.valueOf(name);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
