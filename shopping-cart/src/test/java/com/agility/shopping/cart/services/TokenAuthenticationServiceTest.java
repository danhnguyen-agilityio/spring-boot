package com.agility.shopping.cart.services;

import com.agility.shopping.cart.constants.RoleType;
import lombok.val;
import org.junit.Test;

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

}
