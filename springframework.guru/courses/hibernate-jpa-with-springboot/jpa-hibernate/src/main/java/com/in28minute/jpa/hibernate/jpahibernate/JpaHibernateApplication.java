package com.in28minute.jpa.hibernate.jpahibernate;

import com.in28minute.jpa.hibernate.jpahibernate.entity.Course;
import com.in28minute.jpa.hibernate.jpahibernate.repository.CourseRepository;
import com.in28minute.jpa.hibernate.jpahibernate.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JpaHibernateApplication implements CommandLineRunner {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	StudentRepository studentRepository;

	public static void main(String[] args) {
		SpringApplication.run(JpaHibernateApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		Course course = courseRepository.findById(10001L);
//		logger.info("{}", course);
//
//		courseRepository.deleteById(10003L);
//

//		courseRepository.save(new Course("Microservice"));

//		studentRepository.saveStudentWithPassport();

		courseRepository.addReviewsForCourse();
	}
}

