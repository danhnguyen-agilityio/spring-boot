package com.agility.usermanagement.constants;

/**
 * Defines name role
 */
public enum RoleName {
    USER("ROLE_USER"),
    MANAGER("ROLE_MANAGER"),
    ADMIN("ROLE_ADMIN");

    private String name;

    RoleName(String name) {
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
}
