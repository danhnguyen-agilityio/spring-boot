package com.apress.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.PrintStream;
import java.util.List;

@SpringBootApplication
//public class DemoApplication implements CommandLineRunner, ApplicationRunner {
  public class DemoApplication {
  private static final org.slf4j.Logger log = LoggerFactory.getLogger(DemoApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

  @Bean
  String info() {
    return "Just a simple String bean";
  }

  @Autowired
  String info;

//  @Override
  public void run(ApplicationArguments args) throws Exception {
    log.info("## > ApplicationRunner Implementation...");
    log.info("Accessing the Info bean: " +  info);
    args.getNonOptionArgs().forEach(file -> log.info(file));
  }

//  @Override
  public void run(String... args) throws Exception {
    log.info("## > CommandLineRunner Implementation...");
    log.info("Accessing the Info bean: " +  info);
    for (String arg : args) {
      log.info(arg);
    }
  }

  @Bean
  CommandLineRunner myMethod() {
    return args -> {
      log.info("## > CommandLineRunner Implementation...");
      log.info("Accessing the Info bean: " +  info);
      for (String arg : args) {
        log.info(arg);
      }
    };
  }
}

class Demo1 {
  public static void main1(String[] args) {
    if (args != null) {
      System.out.println(args[0]);
    }
    SpringApplication.run(DemoApplication.class, args);
  }

  private static void customBanner(String[] args) {
    SpringApplication app = new SpringApplication(DemoApplication.class);
    app.setBanner(new Banner() {
      @Override
      public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
        out.print("\n\n\tThis is my own banner!\n\n".toUpperCase());
      }
    });
    app.run(args);
  }

  private static void useSpringApplicationBuilder(String[] args) {
    new SpringApplicationBuilder()
        .bannerMode(Banner.Mode.OFF)
        .sources(DemoApplication.class)
        .logStartupInfo(false)
        .profiles("prod", "cloud")
        .run(args);
  }
}

class MyComponent {
  private static final Logger log = (Logger) LoggerFactory.getLogger(MyComponent.class);

  @Autowired
  public MyComponent(ApplicationArguments args) {
    boolean enable = args.containsOption("enable");
    if (enable) {
      log.info("## > You are enable!");
    }

    List<String> _args = args.getNonOptionArgs();
    log.info("## > extra args");

    if (!_args.isEmpty()) {
      _args.forEach(file -> log.info(file));
    }
  }
}
