package com.agility.shopping.cart.securities;

import com.agility.shopping.cart.exceptions.ApiError;
import com.agility.shopping.cart.exceptions.BaseCustomException;
import com.agility.shopping.cart.utils.ConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtTokenFilter extends GenericFilterBean {

    private JwtTokenService jwtTokenService;

    public JwtTokenFilter(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
        throws IOException, ServletException {

        // FIXME:: We can catch exception and set error to body
        /*try {
            log.debug("Implement doFilter");

            String token = jwtTokenService.resolveToken((HttpServletRequest) request);
            if (token != null && jwtTokenService.validateToken(token)) {
                Authentication authentication = jwtTokenService.getAuthentication(token);

                if (authentication != null) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            filterChain.doFilter(request, response);
        } catch (InvalidJwtAuthenticationException e) {
            log.debug("Error get authentication filter");
            setErrorResponse((HttpServletResponse) response, e);
        }*/

        String token = jwtTokenService.resolveToken((HttpServletRequest) request);
        if (token != null && jwtTokenService.validateToken(token)) {
            Authentication authentication = jwtTokenService.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            SecurityContextHolder.getContext().setAuthentication(null);
        }
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
