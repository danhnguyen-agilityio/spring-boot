package com.dogeatdogenterprises.repositories;

import com.dogeatdogenterprises.domain.Product;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by donaldsmallidge on 3/7/17.
 */
public interface ProductRepository extends CrudRepository<Product, Integer> {
}
