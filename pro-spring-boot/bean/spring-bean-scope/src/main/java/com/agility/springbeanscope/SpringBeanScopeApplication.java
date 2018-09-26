package com.agility.springbeanscope;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

// https://grokonez.com/spring-framework/spring-core/spring-bean-scopes-singleton-prototype
// Singleton scope: get same bean instance every time get bean id
// Prototype scope: bean instance is created every time get bean id
@SpringBootApplication
public class SpringBeanScopeApplication implements CommandLineRunner {

	@Autowired
	private ApplicationContext context;

	public static void main(String[] args) {
		SpringApplication.run(SpringBeanScopeApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("------------ Log All Bean -----------------");
		for (String bean : context.getBeanDefinitionNames()) {
			System.out.println(bean);
		}

		System.out.println("------------ Check bean is singleton or prototype -----------------");
		Name name1_1 = (Name) context.getBean("name1");
		Name name1_2 = (Name) context.getBean("name1");
		System.out.println("Bean id name1 is singleton: " + (name1_1 == name1_2));

		Name name2_1 = (Name) context.getBean("name2");
		Name name2_2 = (Name) context.getBean("name2");
		System.out.println("Bean id name1 is singleton: " + (name2_1 == name2_2));

		System.out.println("------------ Check bean inject on customer is singleton or prototype -------------");
		Customer customer1 = (Customer) context.getBean("customer1");
		Customer customer2 = (Customer) context.getBean("customer2");
		System.out.println("Bean id name1 is singleton: " + (customer1.getName() == customer2.getName()));

		Customer customer3 = (Customer) context.getBean("customer3");
		Customer customer4 = (Customer) context.getBean("customer4");
		System.out.println("Bean id name1 is singleton: " + (customer3.getName() == customer4.getName()));
	}
}
