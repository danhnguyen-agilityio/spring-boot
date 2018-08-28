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
    public static void addAuthentication(HttpServletResponse response, String username) {

        Claims claims = Jwts.claims().setSubject(username);

        // FIXME: TODO get from database
        claims.put("roles", "ADMIN");

        String JWT = Jwts.builder()
            .setClaims(claims)
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS512, SECRET)
            .compact();

        response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
    }

    /**
     * Read info from request to get token, then get Authentication from token
     */
    public static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null && token.startsWith(TOKEN_PREFIX)) {
            // Parse the token
            Claims claims = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody();

            // Extract the Username
            String user = claims.getSubject();

            // Extract the Roles
//            ArrayList<String> roles = (ArrayList<String>) claims.get("roles");
            String roles = (String) claims.get("roles");
            logger.info("Roles: {}", roles);
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(roles);

            return (user != null) ?
                new UsernamePasswordAuthenticationToken(user, null, Arrays.asList(grantedAuthority)) :
                null;
        }
        return null;
    }
}
