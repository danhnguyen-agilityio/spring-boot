package com.agility.oauth2example2.service;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.List;

public class DefaultAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        final String userEmail = authentication.getName();
        final Object userPassword = authentication.getCredentials();

        if (userEmail == null || userPassword == null) {
            return null;
        }

        if (userEmail.isEmpty() || userPassword.toString().isEmpty()) {
            return null;
        }

        String validUserEmail = "test@test.com";
        String validUserPassword = "tester";

        String validUserEmail1 = "real@real.com";
        String validUserPassword1 = "realer";

        if (userEmail.equalsIgnoreCase(validUserEmail)
            && userPassword.equals(validUserPassword)) {
            return new UsernamePasswordAuthenticationToken(userEmail, userPassword, getAuthority());
        }

        if (userEmail.equalsIgnoreCase(validUserEmail1)
            && userPassword.equals(validUserPassword1)) {
            return new UsernamePasswordAuthenticationToken(userEmail, userPassword, getAuthority());
        }

        throw new UsernameNotFoundException("Invalid username or password");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private List<SimpleGrantedAuthority> getAuthority() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }
}
