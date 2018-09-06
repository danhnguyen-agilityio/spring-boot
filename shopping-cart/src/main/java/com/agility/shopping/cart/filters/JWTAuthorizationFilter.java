package com.agility.shopping.cart.filters;

import com.agility.shopping.cart.services.TokenAuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.agility.shopping.cart.constants.SecurityConstants.HEADER_STRING;
import static com.agility.shopping.cart.constants.SecurityConstants.TOKEN_PREFIX;

/**
 * This class get authentication from token that is attached in request
 */
@Slf4j
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
        HttpServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        log.debug("Implement Authorization Filter");

        String token = request.getHeader(HEADER_STRING);
        log.debug("Token: {}", token);

        if (token == null || !token.startsWith(TOKEN_PREFIX)) {
            // Token not found
            chain.doFilter(request, response);
            return;
        }

        // Token found - Get authentication
        Authentication authentication = TokenAuthenticationService.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}
