package com.agility.usermanagement.securities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
@Builder
public class SecurityConfig {
    private String secret;
    private String headerString;
    private String tokenPrefix;
    private long expirationTime;
}
