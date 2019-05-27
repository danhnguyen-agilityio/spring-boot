package com.agility.resourceserver.dto;

import lombok.*;

import java.util.List;

/**
 * Encapsulate data in object UserResponse to returned to client
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    Long id;
    String username;
    List<String> roles;
}
