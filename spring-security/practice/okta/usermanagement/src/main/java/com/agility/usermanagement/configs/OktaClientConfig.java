package com.agility.usermanagement.configs;

import com.okta.sdk.authc.credentials.TokenClientCredentials;
import com.okta.sdk.client.Client;
import com.okta.sdk.client.Clients;
import com.okta.spring.boot.sdk.config.OktaClientProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@EnableConfigurationProperties({ OktaClientProperties.class })
public class OktaClientConfig {

    @Bean
    public Client client(OktaClientProperties oktaClientProperties) {
        log.debug("ORG Url: {}", oktaClientProperties.getOrgUrl());
        log.debug("Supper admin token: {}", oktaClientProperties.getToken());
        return Clients.builder()
            .setOrgUrl(oktaClientProperties.getOrgUrl())
            .setClientCredentials(new TokenClientCredentials(oktaClientProperties.getToken()))
            .build();
    }
}
