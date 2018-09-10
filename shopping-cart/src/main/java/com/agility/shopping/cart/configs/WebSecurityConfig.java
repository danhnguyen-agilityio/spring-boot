package com.agility.shopping.cart.configs;

import com.agility.shopping.cart.constants.RoleType;
import com.agility.shopping.cart.filters.JWTAuthenticationFilter;
import com.agility.shopping.cart.filters.JWTAuthorizationFilter;
import com.agility.shopping.cart.repositories.UserRepository;
import com.agility.shopping.cart.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * WebSecurityConfig class config security feature
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String SHOPPING_CART_URL = "/shopping-carts";
    public static final String SHOPPING_CART_DETAIL_URL = "/shopping-carts/{id}";
    public static final String CART_ITEM_URL = "/cart-items";
    public static final String CART_ITEM_DETAIL_URL = "/cart-items/{id}";

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    /**
     * Authenticate credential that user login with username and password
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    /**
     * Authorize requests with roles
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
            // Authenticated user can access to api get product and list product
            .antMatchers(HttpMethod.GET, "/products/**").authenticated()
            // Only user admin can access to api create, update, delete product
            .antMatchers( "/products/**").hasAuthority(RoleType.ADMIN.getName())
            .antMatchers(SHOPPING_CART_URL + "/**").hasAuthority(RoleType.MEMBER.getName())
            .antMatchers(CART_ITEM_URL + "/**").hasAuthority(RoleType.MEMBER.getName())
            .anyRequest().authenticated()
            .and()
            // The authentication filter
            .addFilter(new JWTAuthenticationFilter(authenticationManager(),
                userRepository))
            // The authorization filter
            .addFilter(new JWTAuthorizationFilter(authenticationManager()))
            // Disables session creation on spring security
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }
}
