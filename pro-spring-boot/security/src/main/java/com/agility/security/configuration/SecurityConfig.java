package com.agility.security.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Authentication : User --> Roles
     */
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("user1").password("password").roles("USER")
            .and()
            .withUser("manager1").password("password").roles("MANAGER")
            .and()
            .withUser("admin1").password("password").roles("USER", "ADMIN");
    }

    /**
     * Authorization : Role --> Access
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
            .and().authorizeRequests()
            .antMatchers("/students/**").hasRole("USER")
            .antMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN")
            .antMatchers("/admin/**").hasRole("ADMIN")
            .anyRequest().authenticated() // same syntax antMatchers("/**")
            .and().csrf().disable().headers().frameOptions().disable();
    }
}
