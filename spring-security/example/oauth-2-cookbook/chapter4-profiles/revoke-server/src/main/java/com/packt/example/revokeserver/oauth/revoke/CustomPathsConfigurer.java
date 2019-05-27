package com.packt.example.revokeserver.oauth.revoke;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.ClientDetailsUserDetailsService;

@Configuration
public class CustomPathsConfigurer extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // the size can be between 4 to 31
        return new BCryptPasswordEncoder(4);
    }

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        ClientDetailsUserDetailsService userDetailsService
            = new ClientDetailsUserDetailsService(clientDetailsService);
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .requestMatchers().antMatchers("/oauth/revoke").and()
            .httpBasic().and()
            .authorizeRequests().anyRequest().authenticated().and()
            .csrf().disable();
    }
}
