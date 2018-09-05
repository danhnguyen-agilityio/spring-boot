package com.agility.shopping.cart.services;

import com.agility.shopping.cart.models.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Set;

import static com.agility.shopping.cart.constants.SecurityConstants.EXPIRATION_TIME;
import static com.agility.shopping.cart.constants.SecurityConstants.SECRET;
import static com.agility.shopping.cart.constants.SecurityConstants.TOKEN_PREFIX;

/**
 * This class used to generate and parse token
 */
public class TokenAuthenticationService {

    /**
     * Create token with given username and roles
     * @param username
     * @param roles
     * @return Token generated from username and roles info
     */
    public static String createToken(String username, Set<String> roles) {
        Claims claims = Jwts.claims().setSubject(username);

        if (username != null && username.length() > 0) {
            claims.put("roles", roles);
        }

        // Generate the token
        String token = Jwts.builder()
            .setClaims(claims)
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS512, SECRET)
            .compact();

        return TOKEN_PREFIX + token;
    }
}
