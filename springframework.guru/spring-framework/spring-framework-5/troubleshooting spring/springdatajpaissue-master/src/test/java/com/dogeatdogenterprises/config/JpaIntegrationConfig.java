package com.dogeatdogenterprises.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;

/**
 * Created by donaldsmallidge on 12/6/16.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan("com.dogeatdogenterprises")
public class JpaIntegrationConfig {
    // QUESTION:  How does deprecation of @SpringApplicationConfiguration(JpaIntegrationConfig.class) affect this class?
}
