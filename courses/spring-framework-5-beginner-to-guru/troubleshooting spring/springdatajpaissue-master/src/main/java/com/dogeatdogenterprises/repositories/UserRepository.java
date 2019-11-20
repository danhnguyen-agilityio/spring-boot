package com.dogeatdogenterprises.repositories;

import com.dogeatdogenterprises.domain.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by donaldsmallidge on 3/7/17.
 */
public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsername(String username);
}
