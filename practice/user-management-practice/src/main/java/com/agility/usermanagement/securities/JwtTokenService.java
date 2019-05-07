package com.agility.usermanagement.securities;

import com.agility.usermanagement.exceptions.CustomError;
import com.agility.usermanagement.exceptions.InvalidJwtAuthenticationException;
import com.agility.usermanagement.models.User;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
@Slf4j
public class JwtTokenService {

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private UserDetailsService userDetailsService;

    public JwtTokenService() {
    }

    public JwtTokenService(SecurityConfig securityConfig, UserDetailsService userDetailsService) {
        this.securityConfig = securityConfig;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Create token from given user
     *
     * @param user User info
     * @return Token string
     */
    public String createToken(User user) {
        if (user == null || user.getUsername() == null || user.getId() == null) {
            return null;
        }

        Claims claims = Jwts.claims()
            .setSubject(user.getUsername())
            .setId(user.getId().toString());

        // Generate the token
        String token = Jwts.builder()
            .setClaims(claims)
            .setExpiration(new Date(System.currentTimeMillis() + securityConfig.getExpirationTime()))
            .signWith(SignatureAlgorithm.HS512, securityConfig.getSecret())
            .compact();

        return securityConfig.getTokenPrefix() + " " + token;
    }

    /**
     * Get authentication from token
     *
     * @param token Token is attached in request
     * @return Authentication
     * @throws UsernameNotFoundException when username not found
     * @throws InvalidJwtAuthenticationException when token has expired or invalid
     */
    public Authentication getAuthentication(String token) {
        if (token == null) {
            return null;
        }

        String username;

        try {
            username = Jwts.parser().setSigningKey(securityConfig.getSecret())
                .parseClaimsJws(token.replace(securityConfig.getTokenPrefix(), ""))
                .getBody()
                .getSubject();
        } catch (ExpiredJwtException e) {
            throw new InvalidJwtAuthenticationException(CustomError.EXPIRED_TOKEN);
        }
        catch (JwtException e) {
            throw new InvalidJwtAuthenticationException(CustomError.INVALID_TOKEN);
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (userDetails == null) {
            throw new UsernameNotFoundException("Username " + username + " not found.");
        }

        // Return an authenticated user with the list of Roles attached
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    /**
     * Resolve token from request
     *
     * @param request Http request
     * @return Token string
     */
    public String resolveToken(HttpServletRequest request) {
        String token = request.getHeader(securityConfig.getHeaderString());
        if (token != null && token.startsWith(securityConfig.getTokenPrefix())) {
            return token;
        }
        return null;
    }

    /**
     * Get user id from request
     *
     * @param request Api request
     * @return User id
     */
    public Long getUserId(HttpServletRequest request) {
        try {
            String token = request.getHeader(securityConfig.getHeaderString());

            if (token == null) {
                return null;
            }

            String userId = Jwts.parser().setSigningKey(securityConfig.getSecret())
                .parseClaimsJws(token.replace(securityConfig.getTokenPrefix(), ""))
                .getBody()
                .getId();
            return Long.valueOf(userId);
        } catch (JwtException e) {
            return null;
        }
    }
}
