package com.agility.spring.security;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * Get web service to use security configuration
 */
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {

    public SecurityWebApplicationInitializer() {
        super(SecurityConfig.class);
    }
}
