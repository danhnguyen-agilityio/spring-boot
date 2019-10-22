package com.agility.usermanagement.models;

public enum  Role {
    USER("User"),
    MANAGER("Manager"),
    ADMIN("Admin");

    private String name;

    Role(String name) {
        this.name = name;
    }

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
