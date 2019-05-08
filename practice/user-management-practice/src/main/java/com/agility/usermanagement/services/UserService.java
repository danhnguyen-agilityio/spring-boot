package com.agility.usermanagement.services;

import com.agility.usermanagement.constants.RoleName;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * UserService class
 */
@Service
public class UserService {

    /**
     * Check whether or not current user have given role
     *
     * @param roleName Role name
     * @return boolean if user have given role, other return false
     */
    public boolean haveRole(RoleName roleName) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getAuthorities().stream()
            .map(grantedAuthority -> grantedAuthority.getAuthority())
            .anyMatch(role -> roleName.getName().equals(role));
    }
}
