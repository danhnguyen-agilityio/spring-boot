package com.in28minute.jpa.hibernate.jpahibernate.repository;


import com.in28minute.jpa.hibernate.jpahibernate.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseSpringDataRepository extends JpaRepository<Course, Long> {
}
