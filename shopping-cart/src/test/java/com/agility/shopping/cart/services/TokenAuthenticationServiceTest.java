package com.agility.shopping.cart.services;

import com.agility.shopping.cart.configs.SecurityConfig;
import com.agility.shopping.cart.constants.RoleType;
import com.agility.shopping.cart.models.User;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;

import static com.agility.shopping.cart.utils.FakerUtil.fakeAdminUser;
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

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private TokenAuthenticationService tokenAuthenticationService;

    /**
     * TestModel create token success
     */
    @Test
    public void testCreateToken() {
        String username = "admin";
        val roles = new HashSet<String>();
        roles.add(RoleType.ADMIN.getName());
        String token = tokenAuthenticationService.createToken(username, roles);

        assertNotNull(token);
        assertThat(token, containsString(securityConfig.getTokenPrefix()));
    }

    /**
     * TestModel get authentication success from valid token
     */
    @Test
    public void testGetAuthenticationSuccessFromValidToken() {
        String username = "admin";
        val roles = new HashSet<String>();
        roles.add(RoleType.ADMIN.getName());

        // Create token
        String token = tokenAuthenticationService.createToken(username, roles);

        // Get authentication from token
        Authentication authentication =
            tokenAuthenticationService.getAuthentication(token);

        assertNotNull(authentication);
        assertEquals(authentication.getName(), username);
    }

    /**
     * TestModel get authentication fail from null token
     */
    @Test
    public void testGetAuthenticationFailFromNullToken() {
        // Get authentication from token
        Authentication authentication =
            tokenAuthenticationService.getAuthentication(null);

        assertNull(authentication);
    }

    /**
     * TestModel get authentication fail when token has expired
     */
    @Test(expected = ExpiredJwtException.class)
    public void testGetAuthenticationFailWhenTokenHasExpired() throws Exception {
        // Mock data
        User user = fakeAdminUser();

        // Generate token
        String token = tokenAuthenticationService.createToken(user);

        // Wait time
        Thread.sleep(securityConfig.getExpirationTime());

        // Get authentication from token
        Authentication authentication =
            tokenAuthenticationService.getAuthentication(token);

        assertNotNull(authentication);
    }

    /**
     * TestModel get user id from valid token
     */
    @Test
    public void testGetUserIdFromToken() {
        log.debug("testGetUserIdFromToken: {}", securityConfig.getHeaderString());

        // Mock data
        User user = fakeAdminUser();

        // Generate token
        String token = tokenAuthenticationService.createToken(user);

        // Get user id from token
        Long userId = tokenAuthenticationService.getUserId(token);

        assertNotNull(userId);
        assertEquals(user.getId().toString(), userId.toString());
    }

    /**
     * TestModel get user id from null token
     */
    @Test
    public void testGetUserIdFromNullToken() {
        // Get user id from token
        Long userId = tokenAuthenticationService.getUserId(null);

        assertNull(userId);
    }

}
