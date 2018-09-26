package com.agility.springbeanlifecycle;

import com.agility.springbeanlifecycle.service.CustomerServiceImpCustomMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

// https://grokonez.com/spring-framework/spring-core/spring-bean-life-cycle-callback-interface-custom-method-annotation-aware-interface
// https://grokonez.com/spring-framework/spring-core/spring-bean-post-processors
// https://www.mkyong.com/spring-boot/how-to-display-all-beans-loaded-by-spring-boot/
// The BeanPostProcessor interface helps us provide our own methods before the initialization of beans and likewise after the initialization of the bean
// InitializingBean and DisposableBean callback interfaces will be implemented to override 2 method afterPropertiesSet() and destroy()
// @PostConstruct and @PreDestroy annotations will be annotated to methods:
// @PostConstruct: is called after constructor have bean called
// @PreDestroy: is called before destroy bean
@SpringBootApplication
public class SpringBeanLifecycleApplication implements CommandLineRunner {

	@Autowired
	private ApplicationContext context;

	public static void main(String[] args) {
		SpringApplication.run(SpringBeanLifecycleApplication.class, args);

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean_imp_custom_method.xml");

		System.out.println("Context has been initialized");
		CustomerServiceImpCustomMethod service = (CustomerServiceImpCustomMethod) context.getBean("customerServiceCustomerMethod");
		System.out.println("Already retrieved Bean from context. Next, show Bean data.");
		System.out.println("Customer Name: " + service.getCustomer().getName());
		context.close();

	}

	@Override
	public void run(String... args) throws Exception {
//		System.out.println("All Bean in context");
//		for (String bean : context.getBeanDefinitionNames()) {
//			System.out.println(bean);
//		}
//		System.out.println("Context has been initialized");
//		CustomerServiceImpCustomMethod service = (CustomerServiceImpCustomMethod) context.getBean("customerServiceImpCustomMethod");
//		System.out.println("Already retrieved Bean from context. Next, show Bean data.");
//		System.out.println("Customer Name: " + service.getCustomer().getName());
	}
}
