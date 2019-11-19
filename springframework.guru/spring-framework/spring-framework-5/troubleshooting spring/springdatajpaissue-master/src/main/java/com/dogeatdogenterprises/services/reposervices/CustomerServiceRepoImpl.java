package com.dogeatdogenterprises.services.reposervices;

import com.dogeatdogenterprises.commands.CustomerForm;
import com.dogeatdogenterprises.converters.CustomerFormToCustomer;
import com.dogeatdogenterprises.domain.Customer;
import com.dogeatdogenterprises.repositories.CustomerRepository;
import com.dogeatdogenterprises.repositories.UserRepository;
import com.dogeatdogenterprises.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by donaldsmallidge on 3/7/17.
 */
@Service
@Profile({"springdatajpa"})
public class CustomerServiceRepoImpl implements CustomerService {

    private CustomerRepository customerRepository;
    private UserRepository userRepository;
    private CustomerFormToCustomer customerFormToCustomer;

    @Autowired
    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setCustomerFormToCustomer(CustomerFormToCustomer customerFormToCustomer) {
        this.customerFormToCustomer = customerFormToCustomer;
    }

    @Override
    public List<?> listAll() {
        List<Customer> customers = new ArrayList<>();
        customerRepository.findAll().forEach(customers::add); // fun with Java 8
        return customers;
    }

    @Override
    public Customer getById(Integer id) {
        return customerRepository.findOne(id);
    }

    @Override
    public Customer saveOrUpdate(Customer domainObject) {
        return customerRepository.save(domainObject);
    }

    @Override
    public Customer saveOrUpdateCustomerForm(CustomerForm customerForm) {
        Customer newCustomer = customerFormToCustomer.convert(customerForm);

        if (newCustomer.getUser().getId() != null) {
            Customer existingCustomer = getById(newCustomer.getId());

            newCustomer.getUser().setEnabled(existingCustomer.getUser().getEnabled());
        }
        return saveOrUpdate(newCustomer);
    }

    @Override
    @Transactional
    public void delete(Integer id) {

        Customer customer = customerRepository.findOne(id);

        // Note: @Transactional required to get these to delete manually!
        userRepository.delete(customer.getUser());
        customerRepository.delete(customer);
    }


}
