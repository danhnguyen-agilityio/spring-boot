package com.agility.usermanagement.securities;

import com.agility.usermanagement.models.User;
import com.agility.usermanagement.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Unit test class UserDetailsServiceImpl
 */
@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceImplTest {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Mock
    private UserRepository userRepository;

    private User user;

    @Before
    public void setUp() {
        user = new User();
        user.setUsername("david");
        user.setPassword("nguyen");
    }

    @Test
    public void testLoadUserByUsernameWhenUserFound() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(anyString());

        assertNotNull(userDetails);
        assertEquals("david", userDetails.getUsername());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void testLoadUserByUsernameThrowExceptionWhenUserNotFound() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        userDetailsServiceImpl.loadUserByUsername(anyString());
    }
}