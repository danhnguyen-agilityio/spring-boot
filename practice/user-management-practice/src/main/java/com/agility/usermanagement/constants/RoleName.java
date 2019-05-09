package com.agility.usermanagement.constants;

/**
 * Defines name role
 */
// TODO:: refactor name and method
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

    /**
     * Test RoleName by name
     * @param name role name
     * @return RoleName enum
     */
    public static RoleName getRole(String name) {
        try {
            return RoleName.valueOf(name);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
