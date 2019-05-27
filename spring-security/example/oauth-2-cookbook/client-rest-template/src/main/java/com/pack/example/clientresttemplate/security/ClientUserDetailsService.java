package com.pack.example.clientresttemplate.security;

import com.pack.example.clientresttemplate.user.ClientUser;
import com.pack.example.clientresttemplate.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository users;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<ClientUser> optionalUser = users.findByUsername(username);
        if (!optionalUser.isPresent()) {
            throw new UsernameNotFoundException("invalid username or password");
        }

        return new ClientUserDetails(optionalUser.get());
    }
}
