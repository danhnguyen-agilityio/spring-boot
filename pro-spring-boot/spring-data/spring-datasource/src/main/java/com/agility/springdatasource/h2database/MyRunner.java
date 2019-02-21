package com.agility.springdatasource.h2database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyRunner implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        String sql = "select * from Cars";

        List<Car> cars = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Car.class));

        for (Car car : cars) {
            System.out.println(car);
        }
    }
}
