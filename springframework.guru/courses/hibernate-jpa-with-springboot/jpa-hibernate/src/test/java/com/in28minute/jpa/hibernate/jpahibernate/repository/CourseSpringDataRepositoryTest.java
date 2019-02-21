package com.in28minute.jpa.hibernate.jpahibernate.repository;

import com.in28minute.jpa.hibernate.jpahibernate.entity.Course;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CourseSpringDataRepositoryTest {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CourseSpringDataRepository repository;

    @Test
    public void findById_CoursePresent() {
        Optional<Course> courseOptional = repository.findById(10001L);
        assertTrue(courseOptional.isPresent());
    }

    @Test
    public void findById_CourseNotPresent() {
        Optional<Course> courseOptional = repository.findById(10004L);
        assertFalse(courseOptional.isPresent());
    }

    @Test
    public void playingAroundWithSpringDataRepository() {
        logger.info("Courses -> {}", repository.findAll());
        // Courses -> [[Course David] , [Course Spring Boot in 100 Steps] , [Course Naven] ]

        logger.info("Count -> {}", repository.count());
    }

    @Test
    public void sort() {
        Sort sort = new Sort(Sort.Direction.DESC,"name");
        logger.info("Sorted Courses -> {}", repository.findAll(sort));
        // Sorted Courses -> [[Course Spring Boot in 100 Steps] , [Course Naven] , [Course David] ]
    }

    @Test
    public void pagination() {
        PageRequest pageRequest = PageRequest.of(0, 3);
        Page<Course> firstPage = repository.findAll(pageRequest);
        logger.info("First page -> {}", firstPage.getContent());
        // First page -> [[Course David] , [Course Spring Boot in 100 Steps] , [Course Naven] ]

        Pageable secondPageable = pageRequest.next();
        Page<Course> secondPage = repository.findAll(secondPageable);
        logger.info("Second page -> {}", secondPage.getContent());
        // [[Course Dummy1] , [Course Dummy2] , [Course Dummy3] ]
    }
}