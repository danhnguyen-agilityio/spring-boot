package com.dogeatdogenterprises;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringmvcApplication {

	public static void main(String[] args) {

	    boolean showBeans = false;

		ApplicationContext ctx = SpringApplication.run(SpringmvcApplication.class, args);

		//System.out.println("Beans *-----------------------------------------------* ["+ctx.getBeanDefinitionCount()+"]");
		//System.out.println();

		//if (showBeans) {
        //    for (String name : ctx.getBeanDefinitionNames()) {
        //        System.out.println(name);
        //    }
        //    System.out.println("*-----------------------------------------------------* END OF BEAN COUNT");	}
	}
}
