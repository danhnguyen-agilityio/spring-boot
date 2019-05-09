package com.agility.usermanagement.services;

import com.agility.usermanagement.constants.Role;
import com.agility.usermanagement.models.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    // TODO:: mock UsernameAndPasswordAuthentication
    @Test
    public void test() {
        User user = new User();
        user.setUsername("david");
        user.getRoles().add(Role.USER);
        user.getRoles().add(Role.ADMIN);

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(user);
    }

}