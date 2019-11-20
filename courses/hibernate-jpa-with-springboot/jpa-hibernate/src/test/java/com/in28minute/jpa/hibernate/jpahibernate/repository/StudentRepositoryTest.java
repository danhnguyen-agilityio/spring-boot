package com.in28minute.jpa.hibernate.jpahibernate.repository;

import com.in28minute.jpa.hibernate.jpahibernate.entity.Address;
import com.in28minute.jpa.hibernate.jpahibernate.entity.Course;
import com.in28minute.jpa.hibernate.jpahibernate.entity.Passport;
import com.in28minute.jpa.hibernate.jpahibernate.entity.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentRepositoryTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    EntityManager entityManager;

    @Autowired
    StudentRepository studentRepository;

    @Test
    @Transactional
    public void setAddressDetails() {
        Student student = entityManager.find(Student.class, 20001L);
        student.setAddress(new Address("No 101", "Some Street", "Hyderabad"));
        entityManager.flush();
        logger.info("student -> {}", student);
        logger.info("passport -> {}", student.getPassport());
    }

    @Test
    @Transactional
    public void retrieveStudentAndPassportDetails() {
        Student student = entityManager.find(Student.class, 20001L);
        logger.info("student -> {}", student);
        logger.info("passport -> {}", student.getPassport());
    }

    @Test
    @Transactional
    public void retrievePassportAndStudentDetails() {
        Passport passport = entityManager.find(Passport.class, 40001L);
        logger.info("passport -> {}", passport);
        logger.info("student -> {}", passport.getStudent());
    }

    @Test
    public void testDumpyOperation() {
        studentRepository.someDummyOperation();
    }

    @Test
    @Transactional
    public void retrieveStudentAndCourses() {
        Student student = entityManager.find(Student.class, 20001L);
        logger.info("student -> {}", student);
        logger.info("courses -> {}", student.getCourses());
    }

    @Test
    public void insertStudentAndCourse() {
        studentRepository.insertStudentAndCourse(
            new Student("Jack"),
            new Course("Microservices in 100 steps"));
    }
}
