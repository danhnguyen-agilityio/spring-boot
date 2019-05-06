package com.agility.usermanagement.securities;

import lombok.*;

/**
 * AuthenticationRequest class map data body of request login
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {
    String username;
    String password;
}
