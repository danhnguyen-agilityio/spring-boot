package com.agility.security.services;

import com.agility.security.models.Role;
import com.agility.security.models.UserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
public class MyAppUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (!username.equals("demo"))
            return null;

        Set<Role> roles = new HashSet<>();
        roles.add(new Role(2, "ADMIN"));
        roles.add(new Role(1, "USER"));

        UserInfo activeUserInfo = new UserInfo("demo", "password", roles);

        return new User(activeUserInfo.getUsername(), activeUserInfo.getPassword(), getAuthorities(activeUserInfo));
    }

    private Set<GrantedAuthority> getAuthorities(UserInfo userInfo) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Role role : userInfo.getRoles()) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getRole());
            authorities.add(grantedAuthority);
        }
        return authorities;
    }
}
