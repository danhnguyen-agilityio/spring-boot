package com.agility.resourceserver.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "security.oauth2")
@Getter
@Setter
public class SecurityProperties {

    private String resourceId;
    private String clientId;
    private String clientSecret;
    private String tokenEndpoint;
}
