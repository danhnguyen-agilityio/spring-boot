package com.agility.springJWT.configs;

import com.agility.springJWT.filters.JWTAuthenticationFilter;
import com.agility.springJWT.filters.JWTLoginFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Authentication: User => Roles
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        String userQuery = "select username, password, enabled from \"user\" where  username=?";
        String roleQuery = "select username, role from \"user\" where username=?";

//        auth.inMemoryAuthentication()
//            .withUser("admin").password("password").roles("ADMIN");
//        auth.jdbcAuthentication().dataSource(dataSource)
//            .usersByUsernameQuery(userQuery)
//            .authoritiesByUsernameQuery(roleQuery);
    }

    /**
     * Authorization: Request => Roles
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
            .antMatchers("/").permitAll()
            .antMatchers(HttpMethod.POST, "/login").permitAll()
            .anyRequest().authenticated()
            .and()
            // Handle requests before go to handler in controllers
            .addFilterBefore(new JWTLoginFilter("/login", authenticationManager()),
                UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(new JWTAuthenticationFilter(),
                UsernamePasswordAuthenticationFilter.class);
    }


}
