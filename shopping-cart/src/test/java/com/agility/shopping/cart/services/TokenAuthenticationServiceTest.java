package com.agility.shopping.cart.services;

import com.agility.shopping.cart.configs.SecurityConfig;
import com.agility.shopping.cart.constants.RoleType;
import com.agility.shopping.cart.models.User;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

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
    private FakerService fakerService;

    @Autowired
    private TokenAuthenticationService tokenAuthenticationService;

    /**
     * Test create token
     */
    @Test
    public void testCreateToken() {
        String token = tokenAuthenticationService.createToken(fakerService.fakeUser(RoleType.ADMIN));

        assertNotNull(token);
        assertThat(token, containsString(securityConfig.getTokenPrefix()));
    }

    /**
     * Test get authentication success from valid token
     */
    @Test
    public void testGetAuthentication_ShouldSuccess_WhenTokenValid() {
        // Create token
        User user = fakerService.fakeUser(RoleType.ADMIN);
        String token = tokenAuthenticationService.createToken(user);

        // Get authentication from token
        Authentication authentication =
            tokenAuthenticationService.getAuthentication(token);

        assertNotNull(authentication);
        assertEquals(authentication.getName(), user.getUsername());
    }

    /**
     * Test get authentication fail from null token
     */
    @Test
    public void testGetAuthentication_ShouldFail_WhenTokenIsNull() {
        // Get authentication from token
        Authentication authentication =
            tokenAuthenticationService.getAuthentication(null);

        assertNull(authentication);
    }

    /**
     * Test get authentication fail when token has expired
     */
    @Test(expected = ExpiredJwtException.class)
    public void testGetAuthentication_ShouldThrowExpiredJwtException_WhenTokenHasExpired() throws Exception {
        // Mock data
        User user = fakerService.fakeAdminUser();

        // Generate token
        String token = tokenAuthenticationService.createToken(user);

        // Wait time
        Thread.sleep(securityConfig.getExpirationTime());

        // Get authentication
        tokenAuthenticationService.getAuthentication(token);
    }

    /**
     * Test get user id should return user id when token is valid
     */
    @Test
    public void testGetUserId_ShouldReturnUserId_WhenTokenIsValid() {
        log.debug("testGetUserIdFromToken: {}", securityConfig.getHeaderString());

        // Mock data
        User user = fakerService.fakeAdminUser();

        // Generate token
        String token = tokenAuthenticationService.createToken(user);

        // Get user id from token
        Long userId = tokenAuthenticationService.getUserId(token);

        assertNotNull(userId);
        assertEquals(user.getId().toString(), userId.toString());
    }

    /**
     * Test get user id should return null when empty token
     */
    @Test
    public void testGetUserId_ShouldReturnNull_WhenTokenIsNull() {
        // Get user id from token
        Long userId = tokenAuthenticationService.getUserId("");

        assertNull(userId);
    }

}
