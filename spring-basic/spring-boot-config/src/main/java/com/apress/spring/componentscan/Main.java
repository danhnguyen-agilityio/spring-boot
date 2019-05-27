package com.apress.spring.componentscan;

import com.apress.spring.packageD.DemoBeanD1;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
//@ComponentScan(basePackageClasses = {DemoBeanA.class},
//basePackages = {"com.apress.spring.packageD", "com.apress.spring.packageC"})

// Demo useDefaultFilters
//@ComponentScan(basePackages = "com.apress.spring.componentscan", useDefaultFilters = false)

// Demo include filters use FilterType.ASSIGNABLE_TYPE
//@ComponentScan(basePackages = "com.apress.spring.componentscan", useDefaultFilters = false,
//    includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = DemoBeanB2.class))

// Demo include filters use FilterType.REGEX
//@ComponentScan(basePackages = "com.apress.spring.componentscan", useDefaultFilters = false,
//    includeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*[AB]"))

// Demo include filters use FilterType.ASSIGNABLE_TYPE
@ComponentScan(basePackages = "com.apress.spring.componentscan",
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = DemoBeanB2.class))

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Main.class);
        System.out.println("DemoBeanA: " + context.containsBeanDefinition("demoBeanA"));
        System.out.println("DemoBeanB2: " + context.containsBeanDefinition("demoBeanB2"));
        System.out.println("DemoBeanB3: " + context.containsBeanDefinition("demoBeanB3"));
        System.out.println("DemoBeanC1: " + context.containsBeanDefinition("demoBeanC1"));
        System.out.println("DemoBeanD1: " + context.containsBeanDefinition("demoBeanD1"));
    }
}
