package com.agility.security.configuration;

import com.agility.security.services.MyAppUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private DataSource dataSource;

//    @Value("${spring.queries.users-query}")
//    private String usersQuery;
//
//    @Value("${spring.queries.roles-query}")
//    private String rolesQuery;

    @Autowired
    private MyAppUserDetailsService myAppUserDetailsService;

    /**
     * Authentication : User --> Role
     */
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("user1").password("password").roles("USER")
            .and()
            .withUser("manager1").password("password").roles("MANAGER")
            .and()
            .withUser("admin1").password("password").roles("USER", "ADMIN");

//        auth.jdbcAuthentication()
//            .usersByUsernameQuery(usersQuery)
//            .authoritiesByUsernameQuery(rolesQuery)
//            .dataSource(dataSource)
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        auth.userDetailsService(myAppUserDetailsService);
//            .passwordEncoder(bCryptPasswordEncoder);
    }

    /**
     * Authorization : Role --> Access
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
            .and().authorizeRequests()
            .antMatchers("/admin/**").hasAuthority("ADMIN")
            .antMatchers("/manager/**").hasAnyAuthority("MANAGER", "ADMIN")
            .antMatchers("/students/**").hasAuthority("USER")
            .anyRequest().authenticated() // allow logged user, not allow anonymous user
//            .anyRequest().permitAll() // allow anonymous user or logged user
            .and()
            .formLogin()
            .and()
            .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/login")
            .and().csrf().disable().headers().frameOptions().disable();
    }
}
