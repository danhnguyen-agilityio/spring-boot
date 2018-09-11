package com.agility.shopping.cart.constants;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Define constant value for security
 */
// Lombok setter and getter not know to generate code in this case
//@Getter
//@Setter
//@Configuration
//@ConfigurationProperties(prefix = "security")
@Component
@ConfigurationProperties("security")
public class SecurityConstants {

    public static String SECRET;
    public static long EXPIRATION_TIME;
    public static String TOKEN_PREFIX;
    public static String HEADER_STRING;

    public static String getSECRET() {
        return SECRET;
    }

    public static void setSECRET(String SECRET) {
        SecurityConstants.SECRET = SECRET;
    }

    public static long getExpirationTime() {
        return EXPIRATION_TIME;
    }

    public static void setExpirationTime(long expirationTime) {
        EXPIRATION_TIME = expirationTime;
    }

    public static String getTokenPrefix() {
        return TOKEN_PREFIX;
    }

    public static void setTokenPrefix(String tokenPrefix) {
        TOKEN_PREFIX = tokenPrefix;
    }

    public static String getHeaderString() {
        return HEADER_STRING;
    }

    public static void setHeaderString(String headerString) {
        HEADER_STRING = headerString;
    }
}

