package com.agility.spring.repositorys;

import com.agility.spring.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

  User findByEmail(String email);
}
