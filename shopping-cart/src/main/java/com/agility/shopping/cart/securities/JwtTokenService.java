package com.agility.shopping.cart.securities;

import com.agility.shopping.cart.configs.SecurityConfig;
import com.agility.shopping.cart.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static com.agility.shopping.cart.exceptions.CustomError.INVALID_JWT_AUTHENTICATION;

@Service
@Slf4j
public class JwtTokenService {

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private UserDetailsService userDetailsService;

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

        // Generate the token
        String token = Jwts.builder()
            .setClaims(claims)
            .setExpiration(new Date(System.currentTimeMillis() + securityConfig.getExpirationTime()))
            .signWith(SignatureAlgorithm.HS512, securityConfig.getSecret())
            .compact();

        log.debug("Current time: {}", new Date(System.currentTimeMillis()));
        log.debug("Set expiration time: {}", new Date(System.currentTimeMillis()
            + securityConfig.getExpirationTime()));

        return securityConfig.getTokenPrefix() + " " + token;
    }

    /**
     * Get authentication from token
     *
     * @param token Token is attached in request
     * @return Authentication
     */
    public Authentication getAuthentication(String token) {
        log.debug("Get authentication");

        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
        // TODO:: Check condition if user null, test for that case

        // Return an authenticated user with the list of Roles attached
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    /**
     * Get username from token
     *
     * @param token Token string
     * @return Username of user
     */
    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(securityConfig.getSecret())
            .parseClaimsJws(token.replace(securityConfig.getTokenPrefix(), ""))
            .getBody()
            .getSubject();
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
     * Check whether or not token is valid
     *
     * @param token Token string
     * @return true if token valid, else return false
     */
    // FIXME:: How to handle this exception
    public boolean validateToken(String token) {
        try {
            // Parse the token
            Claims claims = Jwts.parser()
                .setSigningKey(securityConfig.getSecret())
                .parseClaimsJws(token.replace(securityConfig.getTokenPrefix(), ""))
                .getBody();

            if (claims.getExpiration().before(new Date())) {
                return false;
            }
            return true;
        } catch (JwtException e) {
            // throw new InvalidJwtAuthenticationException(INVALID_JWT_AUTHENTICATION);
            return false;
        }
    }

    /**
     * Get user id from token
     *
     * @param token Token is attached in request
     * @return User id
     */
    public Long getUserId(String token) {
        String userId = Jwts.parser().setSigningKey(securityConfig.getSecret())
            .parseClaimsJws(token.replace(securityConfig.getTokenPrefix(), ""))
            .getBody()
            .getId();
        return Long.valueOf(userId);
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
