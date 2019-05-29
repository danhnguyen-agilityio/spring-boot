package com.agility.usermanagement.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "okta.client")
@Setter
@Getter
public class OktaClientProperties {

    private String orgUrl;

    private String apiToken;
}
