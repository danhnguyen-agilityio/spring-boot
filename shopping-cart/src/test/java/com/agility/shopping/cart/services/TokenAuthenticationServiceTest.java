package com.agility.shopping.cart.services;

import com.agility.shopping.cart.constants.RoleType;
import lombok.val;
import org.junit.Test;
import org.springframework.security.core.Authentication;

import java.util.HashSet;

import static com.agility.shopping.cart.constants.SecurityConstants.TOKEN_PREFIX;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

/**
 * This class test TokenAuthenticationService class
 */
public class TokenAuthenticationServiceTest {

    /**
     * Test create token success
     */
    @Test
    public void testCreateToken() {
        String username = "admin";
        val roles = new HashSet<String>();
        roles.add(RoleType.ADMIN.getName());
        String token = TokenAuthenticationService.createToken(username, roles);

        assertNotNull(token);
        assertThat(token, containsString(TOKEN_PREFIX));
    }

    /**
     * Test get authentication success from valid token
     */
    @Test
    public void testGetAuthenticationSuccessFromValidToken() {
        String username = "admin";
        val roles = new HashSet<String>();
        roles.add(RoleType.ADMIN.getName());

        // Create token
        String token = TokenAuthenticationService.createToken(username, roles);

        // Get authentication from token
        Authentication authentication =
            TokenAuthenticationService.getAuthentication(token);

        assertNotNull(authentication);
        assertEquals(authentication.getName(), username);
    }

    /**
     * Test get authentication fail from null token
     */
    @Test
    public void testGetAuthenticationFailFromNullToken() {
        String token = null;

        // Get authentication from token
        Authentication authentication =
            TokenAuthenticationService.getAuthentication(token);

        assertNull(authentication);
    }

    /**
     * Test get authentication fail when token has expired
     */
    // FIXME:: Get configuration security from configuration application to test
    @Test
    public void testGetAuthenticationFailWhenTokenHasExpired() {

    }

}
