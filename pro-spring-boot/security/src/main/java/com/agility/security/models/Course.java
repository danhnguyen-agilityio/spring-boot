package com.agility.security.models;

import java.util.List;

public class Course {
    private String id;
    private String name;
    private String description;
    private List<String> steps;

    public Course(String id, String name, String description, List<String> steps) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.steps = steps;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getSteps() {
        return steps;
    }
}
