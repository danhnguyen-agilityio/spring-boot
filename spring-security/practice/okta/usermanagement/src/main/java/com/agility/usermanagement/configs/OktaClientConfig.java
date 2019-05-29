package com.agility.usermanagement.configs;

import com.okta.sdk.authc.credentials.TokenClientCredentials;
import com.okta.sdk.client.Client;
import com.okta.sdk.client.Clients;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@EnableConfigurationProperties({ OktaClientProperties.class })
// TODO: Use default class OktaClientConfig of library
public class OktaClientConfig {

    @Bean
    public Client client(OktaClientProperties oktaClientProperties) {
        return Clients.builder()
            .setOrgUrl(oktaClientProperties.getOrgUrl())
            .setClientCredentials(new TokenClientCredentials(oktaClientProperties.getApiToken()))
            .build();
    }
}
