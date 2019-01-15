package com.in28minute.jpa.hibernate.jpahibernate;

import com.in28minute.jpa.hibernate.jpahibernate.entity.*;
import com.in28minute.jpa.hibernate.jpahibernate.repository.CourseRepository;
import com.in28minute.jpa.hibernate.jpahibernate.repository.EmployeeRepository;
import com.in28minute.jpa.hibernate.jpahibernate.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class JpaHibernateApplication implements CommandLineRunner {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	EmployeeRepository employeeRepository;

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

//		courseRepository.addReviewsForCourse();

		// Insert reviews for course
//		List<Review> reviews = new ArrayList<>();
//		reviews.add(new Review("5", "so great"));
//		reviews.add(new Review("4", "so bad"));
//		courseRepository.addReviewsForCourse(10003L, reviews);

//		studentRepository.insertStudentAndCourse(
//			new Student("Jack"),
//			new Course("Microservices in 100 steps"));

		insertEmployee();
	}

	private void insertEmployee() {
		employeeRepository.insert(new PartTimeEmployee("Jill", new BigDecimal(50)));
		employeeRepository.insert(new FullTimeEmployee("Jack", new BigDecimal(10000)));

		employeeRepository.retrievePartTimeEmployees();
		employeeRepository.retrieveFullTimeEmployees();
	}
}

