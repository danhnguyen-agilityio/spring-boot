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

    public void setSECRET(String SECRET) {
        SecurityConstants.SECRET = SECRET;
    }

    public void setExpirationTime(long expirationTime) {
        EXPIRATION_TIME = expirationTime;
    }

    public void setTokenPrefix(String tokenPrefix) {
        TOKEN_PREFIX = tokenPrefix;
    }

    public void setHeaderString(String headerString) {
        HEADER_STRING = headerString;
    }
}

