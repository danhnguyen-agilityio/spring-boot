package com.agility.prospring4.annotation.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataConfig {
    @Bean
    public DataSource source() {
        DataSource source = new DataSource();
        source.setUrl();
        source.setUser();
        return source;
    }

    @Bean
    public PlatformTransactionManager manager() {
        PlatformTransactionManager manager = new PlatformTransactionManager();
        manager.setDataSource(source());
        return manager;
    }
}
