package com.in28minute.jpa.hibernate.jpahibernate.repository;

import com.in28minute.jpa.hibernate.jpahibernate.entity.Passport;
import com.in28minute.jpa.hibernate.jpahibernate.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@Transactional
public class StudentRepository {

    @Autowired
    EntityManager entityManager;

    public void saveStudentWithPassport() {
        Passport passport = new Passport("Z123456");
        entityManager.persist(passport);

        Student student = new Student("Mike");
        student.setPassport(passport);
        entityManager.persist(student);
    }

    public void someDummyOperation() {
        // Database operation 1 - Retrieve student
        Student student = entityManager.find(Student.class, 20001L);
        // Persistence Context (student)

        // Database Operation 2 - Retrieve passport
        Passport passport = student.getPassport();
        // Persistence Context (student, passport)

        // Database Operation 3 - update passport
        passport.setNumber("E123457");
        // Persistence Context (student, passport++)

        // Database Operation 4 - update student
        student.setName("Range -updated");
        // Persistence Context (student++, passport++)
    }
}
