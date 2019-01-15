package com.in28minute.jpa.hibernate.jpahibernate.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
//@NamedQuery(name = "query_get_all_course", query = "Select c From Course c")
//@NamedQuery(name = "query_get_100_step_courses", query = "Select c From Course c where name like '$Da'")
@NamedQueries(value = {
    @NamedQuery(name = "query_get_all_course", query = "Select c From Course c"),
    @NamedQuery(name = "query_get_100_step_courses", query = "Select c From Course c where name like '$Da'")
})
public class Course {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @UpdateTimestamp
    private LocalDateTime lastUpdatedDate;

    @CreationTimestamp
    private LocalDateTime createdDate;

    @OneToMany(mappedBy = "course")
    private List<Review> reviews = new ArrayList<>();

    public Course() {
    }

    public Course(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void addReview(Review review) {
        this.reviews.add(review);
    }

    public void removeReview(Review review) {
        this.reviews.remove(review);
    }

    @Override
    public String toString() {
        return String.format("[Course %s] ",name);
    }
}
