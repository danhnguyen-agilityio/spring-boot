package com.agility.spring;

import com.agility.spring.repositorys.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class SqlAppApplication {

    private static final Logger log = LoggerFactory.getLogger(SqlAppApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SqlAppApplication.class, args);
    }

    @Bean
    CommandLineRunner start(UserRepository dao) {
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

    /**
     * Define bean to load message from file messages.properties
     */
    @Bean
    LocalValidatorFactoryBean validator(MessageSource messageSource) {
        LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
        validatorFactoryBean.setValidationMessageSource(messageSource);
        return validatorFactoryBean;
    }
}
