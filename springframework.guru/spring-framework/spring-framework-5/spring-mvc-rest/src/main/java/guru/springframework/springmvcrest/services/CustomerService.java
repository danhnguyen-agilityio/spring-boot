package guru.springframework.springmvcrest.services;

import guru.springframework.springmvcrest.domain.Customer;

import java.util.List;

public interface CustomerService {

    Customer findCustomerById(Long id);

    List<Customer> findAAllCustomers();

    Customer saveCustomer(Customer customer);
}
