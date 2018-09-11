package com.agility.shopping.cart.services;

import com.agility.shopping.cart.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.agility.shopping.cart.constants.SecurityConstants.EXPIRATION_TIME;
import static com.agility.shopping.cart.constants.SecurityConstants.SECRET;
import static com.agility.shopping.cart.constants.SecurityConstants.TOKEN_PREFIX;

/**
 * This class used to generate and parse token
 */
@Slf4j
public class TokenAuthenticationService {

    /**
     * Create token with given username and roles
     *
     * @param username
     * @param roles
     * @return Token generated from username and roles info
     */
    public static String createToken(String username, Set<String> roles) {
        log.debug("Generate token");
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

        log.debug("Current time: {}", new Date(System.currentTimeMillis()));
        log.debug("Set expiration time: {}", new Date(System.currentTimeMillis()
            + EXPIRATION_TIME));

        return TOKEN_PREFIX + " " + token;
    }

    /**
     * Create token from given user
     *
     * @param user User info
     * @return Token string
     */
    public static String createToken(User user) {
        log.debug("Generate token");
        Claims claims = Jwts.claims()
            .setSubject(user.getUsername())
            .setId(user.getId().toString());

        Set<String> roles = user.getRoles().stream()
            .map(role -> role.getName())
            .collect(Collectors.toSet());

        if (user.getUsername() != null && user.getUsername().length() > 0) {
            claims.put("roles", roles);
        }

        // Generate the token
        String token = Jwts.builder()
            .setClaims(claims)
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS512, SECRET)
            .compact();

        log.debug("Current time: {}", new Date(System.currentTimeMillis()));
        log.debug("Set expiration time: {}", new Date(System.currentTimeMillis()
            + EXPIRATION_TIME));

        return TOKEN_PREFIX + token;
    }

    /**
     * Get authentication from token
     *
     * @param token Token is attached in request
     * @return Authentication
     */
    public static Authentication getAuthentication(String token) {
        log.debug("Get authentication from token");

        if (token == null || !token.startsWith(TOKEN_PREFIX)) {
            return null;
        }

        // FIXME:: Consider try catch here, thrown ExpiredTokenException when time is expired
        // Parse the token
        Claims claims = Jwts.parser()
            .setSigningKey(SECRET)
            .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
            .getBody();

        // Extract the username
        String user = claims.getSubject();
        log.debug("Username after parse: {}", user);
        if (user == null) return null;

        // Extract the Roles
        Collection<String> roles = (Collection<String>) claims.get("roles");

        // Then convert Roles to GrantedAuthority object for injecting
        val authorities = new HashSet<GrantedAuthority>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }

        // Return an authenticated user with the list of Roles attached
        return new UsernamePasswordAuthenticationToken(user, null, authorities);
    }

    /**
     * Get user id from token
     *
     * @param token Token is attached in request
     * @return User id
     */
    public static Long getUserId(String token) {
        log.debug("Get user id from token");
        if (token == null || !token.startsWith(TOKEN_PREFIX)) {
            return null;
        }

        // Parse the token
        Claims claims = Jwts.parser()
            .setSigningKey(SECRET)
            .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
            .getBody();

        log.debug("User id {}", claims.getId());

        return Long.parseLong(claims.getId());
    }
}
