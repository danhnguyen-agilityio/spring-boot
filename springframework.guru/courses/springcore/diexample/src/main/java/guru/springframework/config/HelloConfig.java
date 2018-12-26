package guru.springframework.config;

import guru.springframework.services.HelloWorldFactory;
import guru.springframework.services.HelloWorldService;
import guru.springframework.services.HelloWorldServiceEnglishImpl;
import guru.springframework.services.HelloWorldServiceSpanishImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
public class HelloConfig {

//    @Bean
//    public HelloWorldFactory helloWorldFactory() {
//        return new HelloWorldFactory();
//    }
//
//    @Bean
//    @Profile({"default", "english"})
//    @Primary
//    public HelloWorldService helloWorldServiceEnglish(HelloWorldFactory helloWorldFactory) {
//        return helloWorldFactory.createHelloWorldService("en");
//    }
//
//    @Bean
//    @Profile("spanish")
//    @Primary
//    public HelloWorldService helloWorldServiceSpanish(HelloWorldFactory helloWorldFactory) {
//        return helloWorldFactory.createHelloWorldService("sp");
//    }
//
//    @Bean
//    public HelloWorldService helloWorldServiceGerman(HelloWorldFactory helloWorldFactory) {
//        return helloWorldFactory.createHelloWorldService("ge");
//    }
//
//    @Bean(name = "french")
//    public HelloWorldService helloWorldServiceFrench(HelloWorldFactory helloWorldFactory) {
//        return helloWorldFactory.createHelloWorldService("fr");
//    }
}
