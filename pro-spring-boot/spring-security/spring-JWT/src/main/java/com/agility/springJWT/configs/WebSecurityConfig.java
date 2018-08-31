package com.agility.springJWT.configs;

import com.agility.springJWT.filters.JWTAuthenticationFilter;
import com.agility.springJWT.filters.JWTAuthorizationFilter;
import com.agility.springJWT.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;

    /**
     * Authentication: User => Roles
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("demo").password("demo").authorities("ADMIN");
//        auth.jdbcAuthentication().dataSource(dataSource)
//            .usersByUsernameQuery(userQuery)
//            .authoritiesByUsernameQuery(roleQuery);

        auth.userDetailsService(userService);
    }

    /**
     * Authorization: Request => Roles
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
            .antMatchers("/").permitAll()
//            .antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL).permitAll()
//            .antMatchers(HttpMethod.POST, "/login").permitAll()
            .antMatchers(HttpMethod.POST, "/sign/in").permitAll()
            .antMatchers("/admin").hasAuthority("ADMIN")
            .antMatchers("/user").hasAuthority("USER")
            .anyRequest().authenticated()
            .and()
            // Handle requests before go to handler in controllers
//            .addFilterBefore(new JWTLoginFilter("/login", authenticationManager()),
//                UsernamePasswordAuthenticationFilter.class)
//            .addFilterBefore(new JWTAuthentication(),
//                UsernamePasswordAuthenticationFilter.class);
            // The authentication filter
            .addFilter(new JWTAuthenticationFilter(authenticationManager(), userService))
            // The authorization filter
            .addFilter(new JWTAuthorizationFilter(authenticationManager()))
            // disables session creation on Spring Security
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }


}
