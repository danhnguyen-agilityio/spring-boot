package com.agility.shopping.cart.services;

import com.agility.shopping.cart.configs.SecurityConfig;
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
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class used to generate and parse token
 */
@Service
@Slf4j
public class TokenAuthenticationService {

    private SecurityConfig securityConfig;

    public TokenAuthenticationService(SecurityConfig securityConfig) {
        this.securityConfig = securityConfig;
    }

    /**
     * Create token from given user
     *
     * @param user User info
     * @return Token string
     */
    public String createToken(User user) {
        log.debug("Generate token");
        Claims claims = Jwts.claims()
            .setSubject(user.getUsername())
            .setId(user.getId().toString());

        Set<String> roles = null;

        if (user.getRoles() != null) {
            roles = user.getRoles().stream()
                .map(role -> role.getName())
                .collect(Collectors.toSet());
        }

        if (user.getUsername() != null && user.getUsername().length() > 0) {
            claims.put("roles", roles);
        }

        // Generate the token
        String token = Jwts.builder()
            .setClaims(claims)
            .setExpiration(new Date(System.currentTimeMillis() + securityConfig.getExpirationTime()))
            .signWith(SignatureAlgorithm.HS512, securityConfig.getSecret())
            .compact();

        log.debug("Current time: {}", new Date(System.currentTimeMillis()));
        log.debug("Set expiration time: {}", new Date(System.currentTimeMillis()
            + securityConfig.getExpirationTime()));

        return securityConfig.getTokenPrefix() + token;
    }

    /**
     * Get authentication from token
     *
     * @param token Token is attached in request
     * @return Authentication
     */
    public Authentication getAuthentication(String token) {
        log.debug("Get authentication from token");

        if (token == null || !token.startsWith(securityConfig.getTokenPrefix())) {
            return null;
        }

        // FIXME:: Consider try catch here, thrown ExpiredTokenException when time is expired
        // Parse the token
        Claims claims = Jwts.parser()
            .setSigningKey(securityConfig.getSecret())
            .parseClaimsJws(token.replace(securityConfig.getTokenPrefix(), ""))
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
    public Long getUserId(String token) {
        log.debug("Get user id from token");
        if (token == null || !token.startsWith(securityConfig.getTokenPrefix())) {
            return null;
        }

        // Parse the token
        Claims claims = Jwts.parser()
            .setSigningKey(securityConfig.getSecret())
            .parseClaimsJws(token.replace(securityConfig.getTokenPrefix(), ""))
            .getBody();

        log.debug("User id {}", claims.getId());

        try {
            return Long.parseLong(claims.getId());
        } catch (NumberFormatException exception) {
            // FIXME:: Consider return null or default value, declare new exception
            return 0L;
        }

    }

    /**
     * Get user id from request
     *
     * @param request Api request
     * @return User id
     */
    public Long getUserId(HttpServletRequest request) {
        String token = request.getHeader(securityConfig.getHeaderString());
        return getUserId(token);
    }

}
