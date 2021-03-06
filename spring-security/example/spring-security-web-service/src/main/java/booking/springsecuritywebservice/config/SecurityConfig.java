package booking.springsecuritywebservice.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("user").password("user").roles("USER")
            .and()
            .withUser("admin").password("admin").roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // instruct Spring to authenticate all requests using the HTTP Basic authentication scheme
        http.authorizeRequests()
            .antMatchers(HttpMethod.GET, "/bookings/**").hasRole("ADMIN")
            .anyRequest().authenticated()
            .and().httpBasic();

        // enable Digest authentication
        /* http.authorizeRequests()
            .anyRequest().authenticated()
            .exceptionHandling()
            .authenticationEntryPoint(digestEntryPoint())
            .and
            .addFilter(digestAuthenticationFilter()); */
    }
}
