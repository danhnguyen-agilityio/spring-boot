package com.agility.usermanagement.repositories;

import com.agility.usermanagement.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser, String> {

    AppUser findByEmail(String email);
}
