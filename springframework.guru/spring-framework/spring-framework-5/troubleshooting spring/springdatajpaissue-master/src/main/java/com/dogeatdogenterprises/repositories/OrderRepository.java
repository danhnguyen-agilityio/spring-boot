package com.dogeatdogenterprises.repositories;

import com.dogeatdogenterprises.domain.Order;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by donaldsmallidge on 3/7/17.
 */
public interface OrderRepository extends CrudRepository<Order, Integer> {
}
