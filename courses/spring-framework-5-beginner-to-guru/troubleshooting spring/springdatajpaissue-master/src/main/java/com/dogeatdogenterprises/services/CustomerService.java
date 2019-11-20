package com.dogeatdogenterprises.services;

import com.dogeatdogenterprises.commands.CustomerForm;
import com.dogeatdogenterprises.domain.Customer;

/**
 * Created by donaldsmallidge on 9/20/16.
 * <p>
 * 3. Create a Customer Service. Use an interface, Create an implementation like we did for the Product object.
 */
public interface CustomerService extends CRUDService<Customer> {

    Customer saveOrUpdateCustomerForm(CustomerForm customerForm);

}

