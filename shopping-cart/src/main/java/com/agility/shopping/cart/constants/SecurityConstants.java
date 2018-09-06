package com.agility.shopping.cart.constants;

/**
 * Define constant value for security
 */
// FIXME:: Should get value from application.yml
public class SecurityConstants {
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 864000000l; // 10 DAYS
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
