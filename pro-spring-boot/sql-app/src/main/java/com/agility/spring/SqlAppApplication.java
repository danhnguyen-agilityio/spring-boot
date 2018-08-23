package com.agility.spring;

import com.agility.spring.models.User;
import com.agility.spring.models.UserDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SqlAppApplication {

	private static final Logger log = LoggerFactory.getLogger(SqlAppApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SqlAppApplication.class, args);
	}

	@Bean
	CommandLineRunner start(UserDAO dao) {
		return args -> {
//			log.info(">>> Create User");
//			dao.save(new User("danhnguyen@gmail.com", "Nguyen Danh"));
//			dao.save(new User("tunguyen@gmail.com", "Nguyen Tu"));
//			dao.save(new User("anhtuan@gmail.com", "Nguyen Tuan Anh"));
//
//			log.info(">>> Delete User");
//			User userWithId1 = new User(1l);
//			dao.delete(userWithId1);
//
//			log.info(">>> Get user by email");
//			User userByEmail = dao.findByEmail("tunguyen@gmail.com");
//			log.info(">>> User by email: " + userByEmail);
//
//			log.info(">>> Update User");
//			userByEmail.setEmail("david@gmail.com");
//			userByEmail.setLastName("David Nguyen");
//			dao.save(userByEmail);
//
//			log.info(">>> Show all user: ");
//			dao.findAll().forEach(user -> System.out.println(user));

		};
	}
}
