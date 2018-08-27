package com.agility.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * Config security
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Autowired
//    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//            .withUser("user").password("{noop}user").roles("USER")
//            .and()
//            .withUser("admin").password("{noop}admin").roles("ADMIN");
//    }

    /**
     * Authenticate all requests using the HTTP Basic authentication scheme
     */
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//            .antMatchers("/", "/users/**").hasRole("ADMIN")
//            .anyRequest().authenticated();
////            .and()
////            .httpBasic();
//    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {

        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        final User.UserBuilder userBuilder = User.builder().passwordEncoder(encoder::encode);
        UserDetails user = userBuilder
            .username("user")
            .password("password")
            .roles("USER")
            .build();

        UserDetails admin = userBuilder
            .username("admin")
            .password("password")
            .roles("USER","ADMIN")
            .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

}
