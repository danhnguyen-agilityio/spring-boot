package com.agility.shopping.cart.configs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Configure CORS global
 * Two way to resolve this problem
 * - Create CorsFilter implement Filter, then set this filter to WebSecurityConfig
 * - Override method addCorsMappings in WebMvcConfigurerAdapter
 */
@Configuration
@Slf4j
public class WebCorsConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        log.debug("Configure CORS global");
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
            .allowedHeaders("*")
            .allowCredentials(true)
            .maxAge(3600);
    }
}
