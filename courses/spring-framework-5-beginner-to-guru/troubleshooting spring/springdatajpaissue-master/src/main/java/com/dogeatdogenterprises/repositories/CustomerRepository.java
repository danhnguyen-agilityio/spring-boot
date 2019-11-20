package com.dogeatdogenterprises.repositories;

import com.dogeatdogenterprises.domain.Customer;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by donaldsmallidge on 3/7/17.
 */
public interface CustomerRepository extends CrudRepository<Customer, Integer> {
}
