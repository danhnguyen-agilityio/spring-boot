package com.in28minute.jpa.hibernate.jpahibernate.repository;

import com.in28minute.jpa.hibernate.jpahibernate.entity.Course;
import com.in28minute.jpa.hibernate.jpahibernate.entity.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class CourseRepository {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    EntityManager entityManager;

    public void findById_Jpql() {
        Query query = entityManager.createQuery("Select c From Course c where c.isDeleted = false");
        List resultList = query.getResultList();
        logger.info("Select c From Course c where c.isDeleted = false -> {}", resultList);
    }

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
    // en.clear() => clear every thing that tracked by entity manager
    // en.refresh(course1) => entity is refreshed with content that comes from the database
    public void testEntityManager() {
        Course course1 = new Course("Web service in 100 steps");
        entityManager.persist(course1);
        entityManager.flush();

        Course course2 = new Course("Angular Js in 100 Steps");
        entityManager.persist(course2);
        entityManager.flush();

//        entityManager.clear();
//        entityManager.detach(course1);
//        entityManager.detach(course2);

        course1.setName("Web service in 100 steps - Updated");
        course2.setName("Angular Js in 100 Steps - Updated");

        entityManager.refresh(course1);

        entityManager.flush();
    }

    public void playWithEntityManager() {
        Course course1 = new Course("Web service");
        entityManager.persist(course1);

        Course course2 = findById(10001L);
        course2.setName("JPA in 50 steps - Updated");
    }

    public void addReviewsForCourse() {
        // get the course 10003
        Course course = findById(10003L);
        logger.info("course.getReviews() -> {}", course.getReviews());

        // add 2 reviews to it
        Review review1 = new Review("5", "so greate ...");
        Review review2 = new Review("4", "very bad");

        // setting the relationship
        course.addReview(review1);
        review1.setCourse(course);

        course.addReview(review2);
        review2.setCourse(course);

        // save it to the database
        entityManager.persist(review1);
        entityManager.persist(review2);
    }

    public void addReviewsForCourse(Long courseId, List<Review> reviews) {
        Course course = findById(courseId);

        for (Review review : reviews) {
            course.addReview(review);
            review.setCourse(course);
            entityManager.persist(review);
        }
    }
}
