package com.agility.profiles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class MultiEvnApplication {

	public static final Logger log = LoggerFactory.getLogger(MultiEvnApplication.class);

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(MultiEvnApplication.class, args);

		log.info("defaultBean: {}", context.containsBeanDefinition("defaultBean"));
		log.info("devBean: {}", context.containsBeanDefinition("devBean"));
		log.info("qaBean: {}", context.containsBeanDefinition("qaBean"));
		log.info("prodBean: {}", context.containsBeanDefinition("prodBean"));

	}

	@Profile("default")
	@Bean
	public String defaultBean() {
		return "default";
	}

	@Profile("dev")
	@Bean
	public String devBean() {
		return "dev";
	}

	@Profile("qa")
	@Bean
	public String qaBean() {
		return "qa";
	}

	@Profile("prod")
	@Bean
	public String prodBean() {
		return "prod";
	}
}
