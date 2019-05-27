package com.agility.shopping.cart.securities;

import com.agility.shopping.cart.constants.RoleType;
import com.agility.shopping.cart.dto.Views;
import com.agility.shopping.cart.models.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.View;

/**
 * SecurityService class
 */
@Service
public class SecurityService {

    /**
     * Get view for current usre
     *
     * @return View
     */
    public Class<? extends View> getViewForCurrentUser() {
        if (haveRole(RoleType.ADMIN)) {
            return Views.Admin.class;
        } else {
            return Views.Member.class;
        }
    }

    /**
     * Check whether or not current user have given role
     *
     * @param roleType Role type
     * @return boolean if user have given role, other return false
     */
    public boolean haveRole(RoleType roleType) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getAuthorities().stream()
            .map(grantedAuthority -> grantedAuthority.getAuthority())
            .anyMatch(role -> roleType.getName().equals(role));
    }

    /**
     * Get current user logged in
     *
     * @return User
     */
    public User getCurrentUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }
}
