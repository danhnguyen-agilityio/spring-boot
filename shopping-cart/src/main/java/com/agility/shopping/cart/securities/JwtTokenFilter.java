package com.agility.shopping.cart.securities;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtTokenFilter  extends GenericFilterBean {

    private JwtTokenService jwtTokenService;

    public JwtTokenFilter(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
        throws IOException, ServletException {

        String token = jwtTokenService.resolveToken((HttpServletRequest) request);
        if (token != null && jwtTokenService.validateToken(token)) {
            Authentication authentication = jwtTokenService.getAuthentication(token);

            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);;
            }
        }
        filterChain.doFilter(request, response );
    }
}
