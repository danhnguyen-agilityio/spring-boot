package com.dogeatdogenterprises.services.mapservices;

import com.dogeatdogenterprises.commands.CustomerForm;
import com.dogeatdogenterprises.converters.CustomerFormToCustomer;
import com.dogeatdogenterprises.domain.Customer;
import com.dogeatdogenterprises.domain.DomainObject;
import com.dogeatdogenterprises.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by donaldsmallidge on 9/20/16.
 *
 * 3. Create a Customer Service. Use an interface, Create an implementation like we did for the Product object.
 */
@Service
@Profile("map")
public class CustomerServiceImpl extends AbstractMapService implements CustomerService {

    private CustomerFormToCustomer customerFormToCustomer;

    @Autowired
    public void setCustomerFormToCustomer(CustomerFormToCustomer customerFormToCustomer) {
        this.customerFormToCustomer = customerFormToCustomer;
    }

    @Override
    public List<DomainObject> listAll() {

        return super.listAll();
    }

    @Override
    public Customer getById(Integer id) {

        return (Customer) super.getById(id);
    }

    @Override
    public Customer saveOrUpdate(Customer domainObject) {

        return (Customer) super.saveOrUpdate(domainObject);
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
    public void delete(Integer id) {

        super.delete(id);
    }

/* SUPERSEDED: see SpringJPABootstrap
    @Override
    protected void loadDomainObjects() {
        domainMap = new HashMap<>();

        Customer customer1 = new Customer();
        customer1.setId(1);
        customer1.setFirstName("Alfred");
        customer1.setLastName("Newman");
        customer1.setAddressLine1("1 Alfred Lane");
        customer1.setAddressLine2("Apt. 101");
        customer1.setEmail("alfred@gmail.com");
        customer1.setPhoneNumber("207-101-1001");
        customer1.setCity("Alfred");
        customer1.setState("Maine");
        customer1.setZipCode("04291");

        domainMap.put(1, customer1);

        Customer customer2 = new Customer();
        customer2.setId(2);
        customer2.setFirstName("Brad");
        customer2.setLastName("Schuster");
        customer2.setAddressLine1("2 Bradford Street");
        customer2.setAddressLine2("Apt. 202");
        customer2.setEmail("bschuster@yahoo.com");
        customer2.setPhoneNumber("207-202-2002");
        customer2.setCity("Waterville");
        customer2.setState("Maine");
        customer2.setZipCode("04902");

        domainMap.put(2, customer2);

        Customer customer3 = new Customer();
        customer3.setId(3);
        customer3.setFirstName("Claire");
        customer3.setLastName("Anthracks");
        customer3.setAddressLine1("3 Claire Avenue");
        customer3.setAddressLine2("Apt. 303");
        customer3.setEmail("claireax@ygmail.com");
        customer3.setPhoneNumber("207-302-3102");
        customer3.setCity("Portland");
        customer3.setState("Maine");
        customer3.setZipCode("04033");

        domainMap.put(3, customer3);

        Customer customer4 = new Customer();
        customer4.setId(4);
        customer4.setFirstName("Dennis");
        customer4.setLastName("LeFleur");
        customer4.setAddressLine1("4 Dentist Road");
        customer4.setAddressLine2("");
        customer4.setEmail("claireax@ygmail.com");
        customer4.setPhoneNumber("207-402-4000");
        customer4.setCity("Bangor");
        customer1.setState("Maine");
        customer1.setZipCode("04734");

        domainMap.put(4, customer4);

        Customer customer5 = new Customer();
        customer5.setId(5);
        customer5.setFirstName("Enid");
        customer5.setLastName("Frommage");
        customer5.setAddressLine1("5 Cheese Way");
        customer5.setAddressLine2("Carriage House Room 3");
        customer5.setEmail("cheezy@ygmail.com");
        customer5.setPhoneNumber("207-502-5500");
        customer5.setCity("South Paris");
        customer5.setState("Maine");
        customer5.setZipCode("04255");

        domainMap.put(5, customer5);
    }
    */
}
