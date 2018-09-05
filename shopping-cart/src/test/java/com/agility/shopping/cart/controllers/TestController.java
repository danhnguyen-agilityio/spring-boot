package com.agility.shopping.cart.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class create fake api to test
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "Test";
    }
}
