package com.agility.spring.model;

import org.springframework.data.repository.CrudRepository;

public interface UserDAO extends CrudRepository<User, Long> {

  User findByEmail(String email);
}
