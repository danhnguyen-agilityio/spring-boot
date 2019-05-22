package com.agility.resourceserver.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.token.*;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_ID = "resource_id";
    private static final String CLIENT_ID = "my-client";
    static final String CLIENT_SECRET = "my-secret";

    @Autowired
    private UserDetailsService userDetailsService;

    // The other way to config check token endpoint instead of config in properties
    @Bean
    public RemoteTokenServices remoteTokenServices() {
        RemoteTokenServices tokenServices = new RemoteTokenServices();
        tokenServices.setClientId(CLIENT_ID);
        tokenServices.setClientSecret(CLIENT_SECRET);
        tokenServices.setCheckTokenEndpointUrl("http://localhost:8080/oauth/check_token");
        return tokenServices;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID).stateless(false);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.
            anonymous().disable()
            .authorizeRequests()
            .anyRequest().authenticated()
            .and().exceptionHandling()
            .accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }
}
