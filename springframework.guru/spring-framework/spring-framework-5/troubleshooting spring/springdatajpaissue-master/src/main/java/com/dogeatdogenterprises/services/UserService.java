package com.dogeatdogenterprises.services;

import com.dogeatdogenterprises.domain.User;

/**
 * Created by donaldsmallidge on 1/8/17.
 */
public interface UserService extends CRUDService<User> {

    User findByUserName(String userName);

}

