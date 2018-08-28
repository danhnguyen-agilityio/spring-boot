package com.agility.springJWT.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Date;

import static com.agility.springJWT.constants.SecurityConstants.*;

public class TokenAuthenticationService {

    /**
     * Write info of JWT to response and send to client side
     */
    public static void addAuthentication(HttpServletResponse response, String username) {
        String JWT = Jwts.builder()
            .setSubject(username)
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
            String user = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody()
                .getSubject();

            return (user != null) ?
                new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList()) :
                null;
        }
        return null;
    }
}
