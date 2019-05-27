package com.agility.usermanagement.controllers;

import com.agility.usermanagement.securities.SecurityConfig;
import com.agility.usermanagement.repositories.UserRepository;
import com.agility.usermanagement.securities.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;

public abstract class BaseController {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected AuthenticationManager authenticationManager;

    @Autowired
    protected JwtTokenService jwtTokenService;

    @Autowired
    protected SecurityConfig securityConfig;
}
