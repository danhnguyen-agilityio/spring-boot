package com.agility.usermanagement.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdatedRequest {

    String email;
    String firstName;
    String lastName;
}
