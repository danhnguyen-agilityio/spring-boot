package com.in28minute.jpa.hibernate.jpahibernate.repository;

import com.in28minute.jpa.hibernate.jpahibernate.entity.Course;
import com.in28minute.jpa.hibernate.jpahibernate.entity.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JPQLTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    EntityManager entityManager;

    @Test
    public void jpql_basic() {
        Query query = entityManager.createNamedQuery("query_get_all_course");
        List resultList = query.getResultList();
        logger.info("Select c From Course -> {}", resultList);
    }

    @Test
    public void jpql_typed() {
        TypedQuery<Course> query =
            entityManager.createNamedQuery("query_get_all_course", Course.class);
        List<Course> resultList = query.getResultList();

        logger.info("Select c From Course -> {}", resultList);
    }

    @Test
    public void jpql_where() {
        TypedQuery<Course> query =
            entityManager.createNamedQuery("query_get_100_step_courses", Course.class);

        List<Course> resultList = query.getResultList();

        logger.info("Select c From Course c where name like '$Da'", resultList);
    }

    @Test
    public void jpql_courses_without_students() {
        TypedQuery<Course> query =
            entityManager.createQuery("Select c from Course c where c.students is empty", Course.class);
        List<Course> resultList = query.getResultList();
        logger.info("Results -> {}", resultList);
    }

    @Test
    public void jpql_courses_with_atleast_2_students() {
        TypedQuery<Course> query = entityManager.createQuery("select c from Course c where size(c.students) > 2", Course.class);
        List<Course> resultList = query.getResultList();
        logger.info("Results -> {}", resultList);
        // Results -> [[Course David] ]
    }

    @Test
    public void jpql_courses_ordered_by_students() {
        TypedQuery<Course> query = entityManager.createQuery("select c from Course c order by size(c.students) desc", Course.class);
        List<Course> resultList = query.getResultList();
        logger.info("Results -> {}", resultList);
        // Results -> [[Course David] ]
    }

    @Test
    public void jpql_students_with_passports_in_a_certain_pattern() {
        TypedQuery<Student> query = entityManager.createQuery(
            "select s from Student s where s.passport.number like '%1234%'", Student.class);
        List<Student> resultList = query.getResultList();
        logger.info("Results -> {}", resultList);
    }

    // like
    // between 100 and 1000
    // is null
    // upper, lower, trim, length

    // JOIN => Select c, s from Course c JOIN c.students s
    // LEFT JOIN => Select c, s from Course c LEFT JOIN c.students s
    // CROSS JOIN => Select c, s from Course c, Student s
    @Test
    public void join() {
        Query query = entityManager.createQuery("Select c, s from Course c JOIN c.students s");
        List<Object[]> resultList = query.getResultList();
        logger.info("Results size -> {}", resultList.size());
        for (Object[] result : resultList) {
            logger.info("Course -> {}, Student -> {}", result[0], result[1]);
        }
    }

    @Test
    public void left_join() {
        Query query = entityManager.createQuery("Select c, s from Course c LEFT JOIN c.students s");
        List<Object[]> resultList = query.getResultList();
        logger.info("Results size -> {}", resultList.size());
        for (Object[] result : resultList) {
            logger.info("Course -> {}, Student -> {}", result[0], result[1]);
        }
    }

    @Test
    public void cross_join() {
        Query query = entityManager.createQuery("Select c, s from Course c, Student s");
        List<Object[]> resultList = query.getResultList();
        logger.info("Results size -> {}", resultList.size());
        for (Object[] result : resultList) {
            logger.info("Course -> {}, Student -> {}", result[0], result[1]);
        }
    }
}
