package com.agility.usermanagement.securities;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

// Lombok setter and getter not know to generate code in this case
//@Getter
//@Setter
//@Configuration
//@ConfigurationProperties(prefix = "security")
@Component
@ConfigurationProperties("security")
@Setter
@Getter
public class SecurityConfig {
    private String secret;
    private String headerString;
    private String tokenPrefix;
    private long expirationTime;
}
