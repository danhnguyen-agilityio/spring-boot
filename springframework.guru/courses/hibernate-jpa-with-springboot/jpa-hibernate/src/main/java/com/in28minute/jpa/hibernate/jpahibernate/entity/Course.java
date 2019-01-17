package com.in28minute.jpa.hibernate.jpahibernate.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
@javax.persistence.Cacheable
@SQLDelete(sql = "update course set is_deleted = true where id = ?")
@Where(clause = "is_deleted = false")
public class Course {

    private static Logger logger = LoggerFactory.getLogger(Course.class);

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

    @ManyToMany(mappedBy = "courses")
    @JsonIgnore
    private List<Student> students = new ArrayList<>();

    private boolean isDeleted;

    // Hibernate don't know when to update isDelete properties, so before remove Course, set isDeleted = true
    // If not change this properties, when use JPQL wil not work correctly because it work base on Entity
    @PreRemove
    private void preRemove() {
        logger.info("Delete column change from false to true");
        this.isDeleted = true;
    }

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

    public List<Student> getStudents() {
        return students;
    }

    public void addStudent(Student student) {
        this.students.add(student);
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        // Should not log info reviews because it will take more sql to query reviews => decrease performance
        //return String.format("[Course %s] Reviews %s", name, reviews);
        return String.format("[Course %s]", name);
    }
}
