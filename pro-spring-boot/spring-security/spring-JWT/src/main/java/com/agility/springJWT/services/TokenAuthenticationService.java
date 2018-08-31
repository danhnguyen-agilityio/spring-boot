package com.agility.springJWT.services;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static com.agility.springJWT.constants.SecurityConstants.*;

public class TokenAuthenticationService {

    public static final Logger logger = LoggerFactory.getLogger(TokenAuthenticationService.class);

    /**
     * Write info of JWT to response and send to client side
     */
    public static void addAuthentication(HttpServletResponse response, Authentication authentication) {

        Claims claims = Jwts.claims().setSubject(authentication.getName());

        List<String> roles = new ArrayList<>();
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            logger.info("Authority of User: " + authority.getAuthority());
            roles.add(authority.getAuthority());
        }

        claims.put("roles", roles);

        logger.info("addAuthentication {}", claims);

        String JWT = Jwts.builder()
            .setClaims(claims)
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS512, SECRET)
            .compact();

        response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
    }

    public static String createToken(String username, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(username);

        if (username != null && username.length() > 0) {
            // To extract the roles and the groups
            claims.put("roles", roles);
            claims.put("groups", Arrays.asList("Beginner", "Advance"));
        }

        // Generate the token
        String token = Jwts.builder()
            .setClaims(claims)
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS512, SECRET)
            .compact();

        return TOKEN_PREFIX  + token;
    }

    /**
     * Read info from request to get token, then get Authentication from token
     */
    public static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null && token.startsWith(TOKEN_PREFIX)) {

            logger.info("Parse JWT");

            // Parse the token
            Claims claims = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody();

            // Extract the Username
            String user = claims.getSubject();
            logger.info("Claims: {}", claims);

            // Extract the Roles
            List<String> roles = (List<String>) claims.get("roles");

            // Then convert Roles to GrantedAuthority Object for injecting
            List<GrantedAuthority> authorities = new ArrayList<>();
            for (String role : roles) {
                logger.info("Role of user after parse: " + role);
                authorities.add(new SimpleGrantedAuthority(role));
            }
//            log.info("Roles: {}", roles);
            logger.info("authorities: {}", authorities);
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ADMIN");


            // Return an Authenticated user with the list of Roles attached
            return (user != null) ?
                new UsernamePasswordAuthenticationToken(user, null, authorities) :
                null;
        }
        return null;
    }
}
