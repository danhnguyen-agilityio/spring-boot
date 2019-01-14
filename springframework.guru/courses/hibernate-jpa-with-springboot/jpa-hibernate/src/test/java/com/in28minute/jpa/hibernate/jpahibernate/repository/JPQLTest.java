package com.in28minute.jpa.hibernate.jpahibernate.repository;

import com.in28minute.jpa.hibernate.jpahibernate.entity.Course;
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
        Query query = entityManager.createQuery("Select c From Course c");
        List resultList = query.getResultList();
        logger.info("Select c From Course -> {}", resultList);
    }

    @Test
    public void jpql_typed() {
        TypedQuery<Course> query =
            entityManager.createQuery("Select c From Course c", Course.class);
        List<Course> resultList = query.getResultList();

        logger.info("Select c From Course -> {}", resultList);
    }

    @Test
    public void jpql_where() {
        TypedQuery<Course> query =
            entityManager.createQuery("Select c From Course c where name like '$Da'", Course.class);

        List<Course> resultList = query.getResultList();

        logger.info("Select c From Course c where name like '$Da'", resultList);
    }
}
