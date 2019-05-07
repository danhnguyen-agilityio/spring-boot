package com.agility.usermanagement.controllers;

import com.agility.usermanagement.repositories.UserRepository;
import com.agility.usermanagement.securities.SecurityConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

/**
 * BaseControllerTest class used to define common data and method mock
 */
@Slf4j
public class BaseControllerTest {

    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Autowired
    protected FilterChainProxy filterChainProxy;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected SecurityConfig securityConfig;

    @MockBean
    protected UserRepository userRepository;

}
