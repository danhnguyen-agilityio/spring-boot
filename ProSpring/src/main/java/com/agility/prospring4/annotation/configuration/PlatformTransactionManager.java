package com.agility.prospring4.annotation.configuration;

public class PlatformTransactionManager {
    DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
