package com.agility.shopping.cart.constants;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Define constant value for security
 */
@ConfigurationProperties(prefix = "security")
public class SecurityConstants {
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 864000000l; // 10 DAYS
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
