package com.agility.spring.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserDAO extends CrudRepository<User, Long> {

  public User findByEmail(String email);
}
