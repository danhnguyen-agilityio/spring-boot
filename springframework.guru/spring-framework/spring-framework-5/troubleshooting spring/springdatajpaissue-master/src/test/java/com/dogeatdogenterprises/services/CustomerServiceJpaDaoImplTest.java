package com.dogeatdogenterprises.services;

/**
 * Created by donaldsmallidge on 1/6/17.
 */

import com.dogeatdogenterprises.config.JpaIntegrationConfig;
import com.dogeatdogenterprises.domain.Customer;
import com.dogeatdogenterprises.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by donaldsmallidge on 12/6/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(JpaIntegrationConfig.class)
// https://spring.io/blog/2016/04/15/testing-improvements-in-spring-boot-1-4
//@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
// not webEnvironment=WebEnvironment.RANDOM_PORT; DEFINED_PORT seems to default to 8080[?])
// http://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/test/context/SpringBootTest.WebEnvironment.html
@ActiveProfiles("jpadao")
public class CustomerServiceJpaDaoImplTest {

    private CustomerService customerService;

    @Autowired
    public void setCustomerService(CustomerService customerService) {

        this.customerService = customerService;
    }

    @Test
    public void testListMethod() throws Exception {
        List<Customer> customers = (List<Customer>)customerService.listAll();
        assert customers.size() == 5;
    }

    @Test
    public void testSaveWithUser() {
        Customer customer = new Customer();
        User user = new User();
        user.setUsername("This is my username");
        user.setPassword("MyAwesomePassword");
        customer.setUser(user);

        Customer savedCustomer1 = customerService.saveOrUpdate(customer);

        assert savedCustomer1.getUser().getId() != null;
    }

    @Test
    public void testGetById() throws Exception {
        Integer id = 1;

        Customer retrievedCustomer = customerService.getById(id);
        assert retrievedCustomer.getFirstName().equalsIgnoreCase("Alfred");
    }

    // ADDITIONAL TESTS NOT IN DOWNLOADED INSTRUCTOR VERSION

    @Test
    public void testSaveOrUpdate() throws Exception {
        Integer id = 3; // <-- Use 3 because 1 is deleted in a different test - 4/13/2017
        String newEmail = "alfrednewman@example.com";
        Customer retrievedCustomer = customerService.getById(id);
        retrievedCustomer.setEmail(newEmail); // <--FAILING 4/13/2017

        Customer modifiedCustomer = customerService.saveOrUpdate(retrievedCustomer);

        assertEquals(id, modifiedCustomer.getId());
        assertEquals(newEmail, modifiedCustomer.getEmail());
    }

    @Test
    public void testDelete() throws Exception {
        Integer id = 1;
        Integer size = 0;

        List<Customer> customers = (List<Customer>)customerService.listAll();
        size = customers.size();
        System.out.println("Number of customers before delete: "+size);
        System.out.println("Attempting to delete customer 1");

        for (Customer c: customers) {
            System.out.println("Customer number: "+c.getId());
        }
        customerService.delete(id); // ISSUE: not being committed before listAll()?

        customers = (List<Customer>)customerService.listAll();

        for (Customer c: customers) {
            if (c.getId() == 1) {
                System.out.println("Customer number: "+c.getId()+" was NOT deleted!");
            }
        }
        System.out.println("Total Customers: "+customers.size() + "; Original Customers: "+size);
        assert customers.size() == (size - 1);
    }
}
