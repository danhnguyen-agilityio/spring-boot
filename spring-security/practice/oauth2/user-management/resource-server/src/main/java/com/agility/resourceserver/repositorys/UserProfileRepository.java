package com.agility.resourceserver.repositorys;


import com.agility.resourceserver.models.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    UserProfile findByUsername(String username);
}
