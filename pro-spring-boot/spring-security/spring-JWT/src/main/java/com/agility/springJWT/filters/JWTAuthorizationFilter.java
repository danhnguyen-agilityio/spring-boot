package com.agility.springJWT.filters;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.agility.springJWT.constants.SecurityConstants.HEADER_STRING;
import static com.agility.springJWT.constants.SecurityConstants.SECRET;
import static com.agility.springJWT.constants.SecurityConstants.TOKEN_PREFIX;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public static final Logger log = LoggerFactory.getLogger(JWTAuthorizationFilter.class);

    /**
     * Generic Constructor
     */
    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain) throws IOException, ServletException {

        String header = request.getHeader(HEADER_STRING);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            // Token not found -leave
            chain.doFilter(request, response);
            return;
        }

        // Token Found - Get authentication
        Authentication authentication = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {

            log.info("Parse JWT");

            // Parse the token
            Claims claims = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody();

            // Extract the Username
            String user = claims.getSubject();
            log.info("Claims: {}", claims);

            // Extract the Roles
            List<String> roles = (List<String>) claims.get("roles");

            // Then convert Roles to GrantedAuthority Object for injecting
            List<GrantedAuthority> authorities = new ArrayList<>();
            for (String role : roles) {
                log.info("Role of user after parse: {}",role);
                authorities.add(new SimpleGrantedAuthority(role));
            }
            log.info("authorities: {}", authorities);

            // Attached the groups to the request attributes for a later usr
            List<String> groups = (List<String>) claims.get("groups");
            request.setAttribute("groups", groups);


            // Return an Authenticated user with the list of Roles attached
            return (user != null) ?
                new UsernamePasswordAuthenticationToken(user, null, authorities) :
                null;
        }
        return null;
    }
}
