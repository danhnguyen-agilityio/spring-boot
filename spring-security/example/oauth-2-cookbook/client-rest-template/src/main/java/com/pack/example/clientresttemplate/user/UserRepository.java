package com.pack.example.clientresttemplate.user;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<ClientUser, Long> {
    Optional<ClientUser> findByUsername(String username);
}
