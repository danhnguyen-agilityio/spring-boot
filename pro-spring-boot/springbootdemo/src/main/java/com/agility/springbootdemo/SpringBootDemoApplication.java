package com.agility.springbootdemo;

import com.agility.springbootdemo.domain.Journal;
import com.agility.springbootdemo.repository.JournalRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootDemoApplication {

  @Bean
  InitializingBean saveData(JournalRepository repo) {
    return () -> {
      repo.save(new Journal("Get to know Spring Boot", "Today I will learn Spring Boot", "01/01/2016"));
      repo.save(new Journal("Simple Spring Boot Project", "I will do my first Spring  Boot Project", "01/02/2016"));
      repo.save(new Journal("Spring Boot Reading", "Read more about Spring Boot", "02/01/2016"));
      repo.save(new Journal("Spring Boot in the Cloud", "Spring Boot using Cloud Foundry", "03/01/2016"));
    };
  }

  public static void main(String[] args) {
    SpringApplication.run(SpringBootDemoApplication.class, args);
  }
}
