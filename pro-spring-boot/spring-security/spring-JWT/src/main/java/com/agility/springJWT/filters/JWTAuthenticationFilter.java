package com.agility.springJWT.filters;

import com.agility.springJWT.configs.WebSecurityConfig;
import com.agility.springJWT.models.AccountCredentials;
import com.agility.springJWT.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
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
import java.util.Arrays;
import java.util.Date;

import static com.agility.springJWT.constants.SecurityConstants.*;

/**
 * This class verify username and password when receiving a request,
 * then it creates a Token for this request. The Tokens contains the Username,
 * Roles and whatever we need
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private UserService userService;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.userService = new UserService();
    }

    /**
     * Verify the identify of the user
     * Is called when the login page is called
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response) throws AuthenticationException {

        AccountCredentials credentials = null;
        try {
            credentials = new ObjectMapper()
            .readValue(request.getInputStream(), AccountCredentials.class);
        } catch (IOException e) {
            credentials = null;
        }

        // Return something that will be invalid if null
        if (credentials == null) {
            return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    null,
                    null,
                    new ArrayList<>()
                )
            );
        } else {
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
        Claims claims = Jwts.claims().setSubject(username);

        if (username != null && username.length() > 0) {
            // To extract the roles and the groups
            claims.put("roles", userService.getRoles(username));
            claims.put("groups", Arrays.asList("Beginner", "Advance"));
        }

        // Generate the token
        String token = Jwts.builder()
            .setClaims(claims)
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS512, SECRET)
            .compact();

        response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + token);

    }
}
