package com.agility.springJWT.filters;

import com.agility.springJWT.models.AccountCredentials;
import com.agility.springJWT.services.TokenAuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * Create JWT and response to user
 */
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    Logger log = LoggerFactory.getLogger(JWTLoginFilter.class);

    public JWTLoginFilter(String url, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
    }

    /**
     * Check data that client sent to server and return object Authentication
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response) throws AuthenticationException,
        IOException, ServletException {

        AccountCredentials credentials = new ObjectMapper()
            .readValue(request.getInputStream(), AccountCredentials.class);

        log.info("attemptAuthentication {}", credentials);

        return getAuthenticationManager().authenticate(
            new UsernamePasswordAuthenticationToken(
                credentials.getUsername(),
                credentials.getPassword(),
                Collections.emptyList()
            )
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, FilterChain chain,
        Authentication authResult) throws IOException, ServletException {

        log.info("Success Authentication with authorities {}", authResult.getAuthorities());

        TokenAuthenticationService.addAuthentication(response, authResult);
    }
}
