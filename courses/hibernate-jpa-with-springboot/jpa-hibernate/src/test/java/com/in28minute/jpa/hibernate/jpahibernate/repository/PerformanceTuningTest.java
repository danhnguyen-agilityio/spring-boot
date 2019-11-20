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

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.Subgraph;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PerformanceTuningTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    EntityManager em;

    @Test
    @Transactional
    public void creatingNPlusOneProblem() {
        List<Course> courses = em
            .createNamedQuery("query_get_all_course")
            .getResultList();

        for (Course course: courses) {
            logger.info("Course -> {} Students -? {}", course, course.getStudents());
        }
    }

    @Test
    @Transactional
    public void solvingNPlusOneProblem_EntityGraph() {
        EntityGraph<Course> entityGraph = em.createEntityGraph(Course.class);
        Subgraph<Object> subGraph = entityGraph.addSubgraph("students");

        List<Course> courses = em
            .createNamedQuery("query_get_all_course")
            .setHint("javax.persistence.loadgraph", entityGraph)
            .getResultList();

        for (Course course: courses) {
            logger.info("Course -> {} Students -? {}", course, course.getStudents());
        }
        // Course -> [Course David] Students -? [[Student Ranga] , [Student Adam] , [Student Jane] ]
        // Course -> [Course Spring Boot in 100 Steps] Students -? []
        // Course -> [Course Naven] Students -? [[Student Ranga] ]
    }

    @Test
    @Transactional
    public void solvingNPlusOneProblem_JoinFetch() {
        List<Course> courses = em
            .createNamedQuery("query_get_all_course_join_fetch")
            .getResultList();

        for (Course course: courses) {
            logger.info("Course -> {} Students -? {}", course, course.getStudents());
        }
        // Course -> [Course David] Students -? [[Student Ranga] , [Student Adam] , [Student Jane] ]
        // Course -> [Course David] Students -? [[Student Ranga] , [Student Adam] , [Student Jane] ]
        // Course -> [Course David] Students -? [[Student Ranga] , [Student Adam] , [Student Jane] ]
        // Course -> [Course Naven] Students -? [[Student Ranga] ]
    }
}
