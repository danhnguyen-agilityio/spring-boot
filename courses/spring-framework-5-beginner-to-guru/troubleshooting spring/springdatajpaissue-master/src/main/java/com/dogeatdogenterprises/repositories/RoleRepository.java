package com.dogeatdogenterprises.repositories;

import com.dogeatdogenterprises.domain.security.Role;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by donaldsmallidge on 3/7/17.
 */
public interface RoleRepository extends CrudRepository<Role, Integer> {
}
