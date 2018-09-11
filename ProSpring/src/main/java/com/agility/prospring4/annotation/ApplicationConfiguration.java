package com.agility.prospring4.annotation;

import com.agility.prospring4.annotation.HelloWorld;
import com.agility.prospring4.annotation.PrintService;
import com.agility.prospring4.annotation.qualifier.Bean1;
import com.agility.prospring4.annotation.qualifier.Bean2;
import com.agility.prospring4.annotation.qualifier.BeanInterface;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public HelloWorld helloWorld() {
        return new HelloWorld();
    }

    @Bean
    PrintService printService() {
        return new PrintService();
    }

    @Bean
    BeanInterface bean1() {
        return new Bean1();
    }

    @Bean
    BeanInterface bean2() {
        return new Bean2();
    }
}
