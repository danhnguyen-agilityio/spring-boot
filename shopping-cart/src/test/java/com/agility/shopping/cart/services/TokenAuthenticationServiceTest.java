package com.agility.shopping.cart.services;

import com.agility.shopping.cart.constants.RoleType;
import com.agility.shopping.cart.models.User;
import com.agility.shopping.cart.utils.FakerUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;

import static com.agility.shopping.cart.constants.SecurityConstants.EXPIRATION_TIME;
import static com.agility.shopping.cart.constants.SecurityConstants.HEADER_STRING;
import static com.agility.shopping.cart.constants.SecurityConstants.TOKEN_PREFIX;
import static com.agility.shopping.cart.utils.FakerUtil.fakeAdminUser;
import static com.agility.shopping.cart.utils.FakerUtil.fakeMemberUser;
import static com.agility.shopping.cart.utils.FakerUtil.fakeUser;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

/**
 * This class test TokenAuthenticationService class
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Slf4j
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
    @Test(expected = ExpiredJwtException.class)
    public void testGetAuthenticationFailWhenTokenHasExpired() throws Exception {
        // Mock data
        User user = fakeAdminUser();

        // Generate token
        String token = TokenAuthenticationService.createToken(user);

        // Wait time
        Thread.sleep(EXPIRATION_TIME);

        // Get authentication from token
        Authentication authentication =
            TokenAuthenticationService.getAuthentication(token);

        assertNotNull(authentication);
    }

    /**
     * Test get user id from valid token
     */
    @Test
    public void testGetUserIdFromToken() {
        log.debug("testGetUserIdFromToken: {}", HEADER_STRING);

        // Mock data
        User user = fakeAdminUser();

        // Generate token
        String token = TokenAuthenticationService.createToken(user);

        // Get user id from token
        Long userId = TokenAuthenticationService.getUserId(token);

        assertNotNull(userId);
        assertEquals(user.getId().toString(), userId.toString());
    }

    /**
     * Test get user id from null token
     */
    @Test
    public void testGetUserIdFromNullToken() {
        // Get user id from token
        Long userId = TokenAuthenticationService.getUserId(null);

        assertNull(userId);
    }

}
