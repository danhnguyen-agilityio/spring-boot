package com.agility.usermanagement.models;

import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void testGetAuthoritiesShouldReturnEmptyWhenUserNotRole() {
        User user = new User();

        List<GrantedAuthority> authorities = (List<GrantedAuthority>) user.getAuthorities();

        assertEquals(authorities.size(), user.getRoles().size());
    }

    @Test
    public void testGetAuthorities() {
        User user = new User();
        user.setRoles(Arrays.asList(Role.MANAGER, Role.ADMIN));

        List<GrantedAuthority> authorities = (List<GrantedAuthority>) user.getAuthorities();

        assertEquals(authorities.size(), user.getRoles().size());
        assertEquals(authorities.get(0).getAuthority(), Role.ROLE_MANAGER);
        assertEquals(authorities.get(1).getAuthority(), Role.ROLE_ADMIN);
    }
}