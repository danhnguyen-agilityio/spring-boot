package com.agility.springdatasource.h2database;


import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

public class AppConfig {

    @Bean
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create()
            .username("sa")
            .password("")
            .url("jdbc:h2:mem:testdb;DB_CLOSE_ON_EXIT=FALSE")
            .driverClassName("org.h2.Driver")
            .build();
    }
}
