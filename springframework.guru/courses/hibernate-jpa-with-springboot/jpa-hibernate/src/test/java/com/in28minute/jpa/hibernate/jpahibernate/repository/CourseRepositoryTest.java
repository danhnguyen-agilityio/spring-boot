package com.in28minute.jpa.hibernate.jpahibernate.repository;

import com.in28minute.jpa.hibernate.jpahibernate.JpaHibernateApplication;
import com.in28minute.jpa.hibernate.jpahibernate.entity.Course;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CourseRepositoryTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CourseRepository courseRepository;

    @Test
    public void findById_basic() {
        Course course = courseRepository.findById(10001L);
        assertEquals("David", course.getName());
    }

    @Test
    @DirtiesContext // Spring would reset the data, so that for the other test, the data has not changed at all
    public void deleteById_basic() {
        courseRepository.deleteById(10001L);
        assertNull(courseRepository.findById(10001L));
    }

    @Test
    @DirtiesContext
    public void save_basic() {
        // get a course
        Course course = courseRepository.findById(10001L);
        assertEquals("David", course.getName());

        // update details
        course.setName("JPA in 50 steps");
        courseRepository.save(course);

        // check the value
        Course course1 = courseRepository.findById(10001L);
        assertEquals("JPA in 50 steps", course.getName());
    }

    @Test
    @DirtiesContext
    public void testEntityManager() {
        courseRepository.testEntityManager();
    }
}