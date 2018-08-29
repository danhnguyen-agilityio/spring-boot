package com.agility.mapstruct.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/test")
    public String test() {
        logger.debug("API /test");
        return "TEST";
    }
}
