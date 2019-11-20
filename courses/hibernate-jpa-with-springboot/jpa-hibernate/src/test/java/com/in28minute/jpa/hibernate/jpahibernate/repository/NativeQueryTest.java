package com.in28minute.jpa.hibernate.jpahibernate.repository;

import com.in28minute.jpa.hibernate.jpahibernate.entity.Course;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NativeQueryTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    EntityManager em;

    @Test
    public void native_query_basic() {
        Query query = em.createNativeQuery("select * from course", Course.class);
        List resultList = query.getResultList();
        logger.info("Select * from course -> {}", resultList);
    }

    @Test
    public void native_query_with_parameter() {
        Query query = em.createNativeQuery("select * from course where id = :id", Course.class);
        query.setParameter("id", 10001L);
        List resultList = query.getResultList();
        logger.info("select * from course where id = :id", resultList );
    }

    @Test
    @Transactional
    public void native_query_to_update() {
        Query query = em.createNativeQuery("Update COURSE set last_updated_date = sysdate()", Course.class);
        int noOfRowUpdated = query.executeUpdate();
        logger.info("noOfRowUpdated -> {}", noOfRowUpdated);
    }
}
