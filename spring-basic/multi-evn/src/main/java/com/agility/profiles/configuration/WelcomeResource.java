package com.agility.profiles.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class WelcomeResource {

    @Value("${welcome.message}")
    private String wellcomeMessage;

    @Autowired
    private BasicConfiguration basicConfiguration;

    @GetMapping("/welcome")
    public String retrieveWelcomeMessage() {
        return wellcomeMessage;
    }

    @RequestMapping("/dynamic")
    public Map<String, Object> dynamic() {
        Map<String, Object> map = new HashMap<>();
        map.put("message", basicConfiguration.getMessage());
        map.put("number", basicConfiguration.getNumber());
        map.put("key", basicConfiguration.isValue());
        return map;
    }
}
