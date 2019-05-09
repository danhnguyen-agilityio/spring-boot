package com.agility.usermanagement.securities;

import com.agility.usermanagement.exceptions.ApiError;
import com.agility.usermanagement.exceptions.BaseCustomException;
import com.agility.usermanagement.exceptions.InvalidJwtAuthenticationException;
import com.agility.usermanagement.utils.ConvertUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtTokenService jwtTokenService;

    public JwtAuthenticationFilter(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication;

        try {
            String token = jwtTokenService.resolveToken(request);
            authentication = jwtTokenService.getAuthentication(token);
        } catch (InvalidJwtAuthenticationException e) {
            authentication = null;
        } catch (Exception e) {
            authentication = null;
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    /**
     * Set error for response
     *
     * @param response Http response
     * @param e        Exception
     * @throws IOException if convert object to json throw exception
     */
    private void setErrorResponse(HttpServletResponse response, BaseCustomException e) throws IOException {
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(e.getHttpStatus().value());
        ApiError apiError = new ApiError(e.getCode(), e.getMessage());
        response.getWriter().write(ConvertUtil.convertObjectToJsonString(apiError));
    }
}
