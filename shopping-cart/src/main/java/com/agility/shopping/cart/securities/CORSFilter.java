package com.agility.shopping.cart.securities;

import lombok.extern.slf4j.Slf4j;
import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class CORSFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        log.debug("Filtering on...........................................................");
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // Set to specific origin
        // response.setHeader("Access-Control-Allow-Origin", "https://www.google.com.vn");
        response.setHeader("Access-Control-Allow-Origin", "*");
        // Meaning of Access-Control-Allow-Credentials
            // Create request with Cookies, namely the withCredentials boolean value
            // Browser will ignore any response that does not have the Access-Control-Allow-Credentials: true header
            // and not make the response available to the invoking web content
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        // Access-Control-Max-Age gives the value in seconds for how long the response to the preflight request
        // can be cached for without sending another prefight request
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, Authorization, Origin, " +
            "Accept, Access-Control-Request-Method, Access-Control-Request-Headers");

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
