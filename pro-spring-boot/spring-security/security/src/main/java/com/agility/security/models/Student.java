package com.agility.security.models;

import java.util.List;

public class Student {
    private String id;
    private String name;
    private String description;
    private List<Course> courses;

    public Student(String id, String name, String description, List<Course> courses) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.courses = courses;
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

    public List<Course> getCourses() {
        return courses;
    }
}
