package com.agility.shopping.cart.filters;

import com.agility.shopping.cart.configs.SecurityConfig;
import com.agility.shopping.cart.models.AccountCredential;
import com.agility.shopping.cart.models.User;
import com.agility.shopping.cart.repositories.UserRepository;
import com.agility.shopping.cart.services.TokenAuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;

/**
 * This class verify username and password when receive a request login,
 * then it creates a Token for this request.
 * The Token contains the username, roles and whatever we need
 */

@Service
@Slf4j
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenAuthenticationService tokenAuthenticationService;

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
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
            return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                    null,
                    null,
                    new HashSet<>())
            );
        } else {
            return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                    credential.getUsername(),
                    credential.getPassword(),
                    new HashSet<>()
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

        log.info("Authentication success");
        String username = ((UserDetails) authResult.getPrincipal()).getUsername();
        User user = userRepository.findByUsername(username);
        String token = tokenAuthenticationService.createToken(user);
        response.addHeader(securityConfig.getHeaderString(), token);

    }
}
