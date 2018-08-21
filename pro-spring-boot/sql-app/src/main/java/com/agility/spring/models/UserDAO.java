package com.agility.spring.models;

import org.springframework.data.repository.CrudRepository;

public interface UserDAO extends CrudRepository<User, Long> {

  User findByEmail(String email);
}
