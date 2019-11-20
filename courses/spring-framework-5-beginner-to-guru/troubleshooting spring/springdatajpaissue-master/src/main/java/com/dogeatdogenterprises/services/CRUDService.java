package com.dogeatdogenterprises.services;

import java.util.List;

/**
 * Created by donaldsmallidge on 9/21/16.
 */
public interface CRUDService<T> {

    List<?> listAll();

    T getById(Integer id);

    T saveOrUpdate(T domainObject);

    void delete(Integer id);
}
