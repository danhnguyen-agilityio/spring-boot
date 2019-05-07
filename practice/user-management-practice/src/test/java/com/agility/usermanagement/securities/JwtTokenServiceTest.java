package com.agility.usermanagement.securities;

import com.agility.usermanagement.exceptions.CustomError;
import com.agility.usermanagement.exceptions.InvalidJwtAuthenticationException;
import com.agility.usermanagement.models.User;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit test class JwtTokenService
 */
@RunWith(MockitoJUnitRunner.class)
public class JwtTokenServiceTest {

    private User user;

    private SecurityConfig securityConfig;

    @Mock
    private UserDetailsService userDetailsService;

    private JwtTokenService jwtTokenService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        user = new User();
        user.setId(100L);
        user.setUsername("david");

//        securityConfig = SecurityConfig.builder()
//            .secret("SecretKeyToGenJWTs")
//            .expirationTime((3000))
//            .tokenPrefix("Bearer")
//            .headerString("Authorization")
//            .build();

        jwtTokenService = new JwtTokenService(securityConfig, userDetailsService);
    }

    /* =================== Test createToken method =============== */

    @Test
    public void testCreateToken() {
        String token = jwtTokenService.createToken(user);

        assertNotNull(token);
        assertThat(token, containsString(securityConfig.getTokenPrefix()));
    }

    @Test
    public void testCreateTokenShouldReturnNullWhenUserNull() {
        String token = jwtTokenService.createToken(null);

        assertNull(token);
    }

    @Test
    public void testCreateTokenShouldReturnNullWhenNullUsername() {
        String token = jwtTokenService.createToken(new User());

        assertNull(token);
    }

    /* =================== Test getAuthentication method  =============== */

    /**
     * Test get authentication success from valid token
     */
    @Test
    public void testGetAuthenticationShouldSuccessWhenTokenValid() {
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(user);

        // Create token
        String token = jwtTokenService.createToken(user);

        // Get authentication from token
        Authentication authentication = jwtTokenService.getAuthentication(token);

        assertNotNull(authentication);
        assertEquals(authentication.getName(), user.getUsername());
    }

    /**
     * Test get authentication return null from null token
     */
    @Test
    public void testGetAuthenticationShouldReturnNullWhenTokenIsNull() {
        // Get authentication from token
        Authentication authentication = jwtTokenService.getAuthentication(null);

        assertNull(authentication);
    }

    /**
     * Test get authentication should throw username not found exception when username not found
     */
    @Test(expected = UsernameNotFoundException.class)
    public void testGetAuthenticationShouldThrowExceptionWhenUsernameNotFound() {
        // mock data
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(null);

        // Create token
        String token = jwtTokenService.createToken(user);

        // Get authentication from token
        Authentication authentication = jwtTokenService.getAuthentication(token);

        assertNull(authentication);
    }

    /**
     * Test get authentication should throw expired token exception when token has expired
     */
    @Test
    public void testGetAuthenticationShouldThrowExpiredTokenExceptionWhenTokenHasExpired() throws Exception {
        thrown.expect(InvalidJwtAuthenticationException.class);
        thrown.expectMessage(CustomError.EXPIRED_TOKEN.message());

        // Generate token
        String token = jwtTokenService.createToken(user);

        // Wait time
        Thread.sleep(securityConfig.getExpirationTime());

        // Get authentication
        jwtTokenService.getAuthentication(token);
    }

    /**
     * Test get authentication should throw invalid token exception when token invalid
     */
    @Test
    public void testGetAuthenticationShouldThrowInvalidTokenExceptionWhenTokenInvalid() throws Exception {
        thrown.expect(InvalidJwtAuthenticationException.class);
        thrown.expectMessage(CustomError.INVALID_TOKEN.message());

        // Generate invalid token
        String token = "this is invalid token";

        // Get authentication
        jwtTokenService.getAuthentication(token);
    }

    /* ========================= Test resolveToken method  =============================== */

    /**
     * Test resolve token should return null when token in request null
     */
    @Test
    public void testResolveTokenShouldReturnNullWhenTokenNull() {
        // mock HttpServletRequest
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getHeader(anyString())).thenReturn(anyString()).thenReturn(null);

        String resolvedToken = jwtTokenService.resolveToken(request);

        assertNull(resolvedToken);
    }

    /**
     * Test resolve token should return null when token not prefix with Bearer
     */
    @Test
    public void testResolveTokenShouldReturnNullWhenTokenNotPrefixWithBearer() {
        // mock HttpServletRequest
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getHeader(anyString())).thenReturn(anyString()).thenReturn("yxxaaaasddddaasdffgge-wwww-accfdaaewaas");

        String resolvedToken = jwtTokenService.resolveToken(request);

        assertNull(resolvedToken);
    }

    @Test
    public void testResolveTokenShouldSuccessWhenToken() {
        // mock HttpServletRequest
        HttpServletRequest request = mock(HttpServletRequest.class);

        String token = jwtTokenService.createToken(user);

        when(request.getHeader(anyString())).thenReturn(token);

        String resolvedToken = jwtTokenService.resolveToken(request);

        assertNotNull(resolvedToken);
    }

    /* ========================= Test getUserId method  =============================== */

    @Test
    public void testGetUserIdShouldReturnNullWhenTokenNull() {
        // mock HttpServletRequest
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getHeader(anyString())).thenReturn(null);

        Long userId = jwtTokenService.getUserId(request);

        assertNull(userId);
    }

    @Test
    public void testGetUserIdShouldReturnNullWhenTokenInvalid() {
        // mock HttpServletRequest
        HttpServletRequest request = mock(HttpServletRequest.class);

        String invalidToken = "this is invalid token";
        when(request.getHeader(anyString())).thenReturn(invalidToken);

        Long userId = jwtTokenService.getUserId(request);

        assertNull(userId);
    }

    @Test
    public void testGetUserIdShouldReturnSuccessWhenTokenValid() {
        // mock HttpServletRequest
        HttpServletRequest request = mock(HttpServletRequest.class);

        String token = jwtTokenService.createToken(user);
        when(request.getHeader(anyString())).thenReturn(token);

        Long userId = jwtTokenService.getUserId(request);

        assertNotNull(userId);
        assertEquals(userId, user.getId());
    }
}