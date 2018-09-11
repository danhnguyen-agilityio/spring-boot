package com.agility.springJWT.filters;

import com.agility.springJWT.models.AccountCredentials;
import com.agility.springJWT.services.TokenAuthenticationService;
import com.agility.springJWT.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.agility.springJWT.constants.SecurityConstants.*;

/**
 * This class verify username and password when receiving a request,
 * then it creates a Token for this request. The Tokens contains the Username,
 * Roles and whatever we need
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    private AuthenticationManager authenticationManager;
    private UserService userService;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    /**
     * Verify the identify of the user
     * Is called when the login page is called
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response) throws AuthenticationException {

        logger.info("Authentication Process");

        AccountCredentials credentials = null;
        try {
            credentials = new ObjectMapper()
            .readValue(request.getInputStream(), AccountCredentials.class);
        } catch (IOException e) {
            credentials = null;
        }

        // Return something that will be invalid if null
        if (credentials == null) {
            logger.info("AccountCredentials: {}", credentials);
            return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    null,
                    null,
                    new ArrayList<>()
                )
            );
        } else {
            logger.info("Username: {}", credentials.getUsername());
            logger.info("Username: {}", credentials.getPassword());
            return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    credentials.getUsername(),
                    credentials.getPassword(),
                    new ArrayList<>()
                )
            );
        }
    }

    /**
     * Generate token and writ to response
     *      - Is called when authentication successfully
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain,
        Authentication authResult) throws IOException, ServletException {

        String username = ((UserDetails)authResult.getPrincipal()).getUsername();

        String token = TokenAuthenticationService.createToken(username,
            userService.getRoles(username));

        response.addHeader(HEADER_STRING, token);

    }
}
