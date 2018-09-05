package com.agility.shopping.cart.filters;

import com.agility.shopping.cart.models.AccountCredential;
import com.agility.shopping.cart.services.TokenAuthenticationService;
import com.agility.shopping.cart.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static com.agility.shopping.cart.constants.SecurityConstants.HEADER_STRING;

/**
 * This class verify username and password when receive a request login,
 * then it creates a Token for this request.
 * The Token contains the username, roles and whatever we need
 */
@Slf4j
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private UserService userService;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager,
        UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    /**
     * Authenticate the identify of the user
     * Is called when the login api is called
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response) throws AuthenticationException {

        log.debug("Authenticate username and password");

        AccountCredential credential;
        try {
            credential = new ObjectMapper()
                .readValue(request.getInputStream(), AccountCredential.class);
        } catch (IOException e) {
            credential = null;
        }

        if (credential == null) {
            return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    null,
                    null,
                    new ArrayList<>())
            );
        } else {
            return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    credential.getUsername(),
                    credential.getPassword(),
                    new ArrayList<>()
                )
            );
        }
    }

    /**
     * Generate token and write to response
     * Is called when authentication success
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, FilterChain chain,
        Authentication authResult) throws IOException, ServletException {

        String username = ((UserDetails) authResult.getPrincipal()).getUsername();
        String token = TokenAuthenticationService.createToken(username,
            userService.getNameRolesByUsername(username));
        response.addHeader(HEADER_STRING, token);

    }
}
