package com.agility.usermanagement.controllers;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

public class BaseControllerTest {

    protected Faker faker = new Faker();

    @Autowired
    protected MockMvc mockMvc;
}
