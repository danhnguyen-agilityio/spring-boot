package com.agility.springdatasource.h2database;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class AppConfig {

    @Bean
//    @Primary
    @ConfigurationProperties(prefix = "datasource")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create()
            .username("sa")
            .password("")
            .url("jdbc:h2:mem:testdb;DB_CLOSE_ON_EXIT=FALSE")
            .driverClassName("org.h2.Driver")
            .build();
//        return DataSourceBuilder.create().build();
    }
}
