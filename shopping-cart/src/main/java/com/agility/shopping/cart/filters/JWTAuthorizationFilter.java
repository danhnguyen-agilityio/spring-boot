package com.agility.shopping.cart.filters;

import com.agility.shopping.cart.configs.SecurityConfig;
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


/**
 * This class get authentication from token that is attached in request
 */
@Slf4j
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private SecurityConfig securityConfig;
    private TokenAuthenticationService tokenAuthenticationService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager,
                                  SecurityConfig securityConfig,
                                  TokenAuthenticationService tokenAuthenticationService) {
        super(authenticationManager);
        this.securityConfig = securityConfig;
        this.tokenAuthenticationService = tokenAuthenticationService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        log.debug("Implement Authorization Filter");

        String token = request.getHeader(securityConfig.getHeaderString());
        log.debug("Token: {}", token);

        if (token == null || !token.startsWith(securityConfig.getTokenPrefix())) {
            // Token not found
            chain.doFilter(request, response);
            return;
        }

        // Token found - Get authentication
        Authentication authentication = tokenAuthenticationService.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}
