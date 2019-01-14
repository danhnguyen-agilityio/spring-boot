package com.in28minute.jpa.hibernate.jpahibernate.repository;

import com.in28minute.jpa.hibernate.jpahibernate.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@Transactional
public class CourseRepository {

    @Autowired
    EntityManager entityManager;

    public Course findById(Long id) {
        return entityManager.find(Course.class, id);
    }

    public void deleteById(Long id) {
        Course course = findById(id);
        entityManager.remove(course);
    }

    public void save(Course course) {
        if (course.getId() == null) {
            entityManager.persist(course);
        } else {
            entityManager.merge(course);
        }
    }


    // Entity manager keep track course when execute persist method, auto update down database when set name of course
    // en.flush() => The changes which are done until then they would be send out to database
    // en.detach(course2) => untrack specific entity
    // en.clear() => clear every thin that tracked by entity manager
    public void testEntityManager() {
        Course course1 = new Course("Web service in 100 steps");
        entityManager.persist(course1);
        entityManager.flush();

        Course course2 = new Course("Angular Js in 100 Steps");
        entityManager.persist(course2);
        entityManager.flush();

        entityManager.clear();
//        entityManager.detach(course1);
//        entityManager.detach(course2);

        course1.setName("Web service in 100 steps - Updated");
        entityManager.flush();

        course2.setName("Angular Js in 100 Steps - Updated");
        entityManager.flush();
    }
}
