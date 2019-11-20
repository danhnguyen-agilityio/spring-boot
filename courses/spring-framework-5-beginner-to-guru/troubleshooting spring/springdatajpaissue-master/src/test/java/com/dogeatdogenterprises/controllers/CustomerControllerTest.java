package com.dogeatdogenterprises.controllers;

import com.dogeatdogenterprises.commands.CustomerForm;
import com.dogeatdogenterprises.commands.validators.CustomerFormValidator;
import com.dogeatdogenterprises.controllers.CustomerController;
import com.dogeatdogenterprises.converters.CustomerToCustomerForm;
import com.dogeatdogenterprises.domain.Address;
import com.dogeatdogenterprises.domain.Customer;
import com.dogeatdogenterprises.domain.User;
import com.dogeatdogenterprises.services.CustomerService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by donaldsmallidge on 11/21/16.
 */
public class CustomerControllerTest {

    //Mockito Mock object
    @Mock
    private CustomerService customerService;

    // sets up controller, and injects mock objects into it
    @InjectMocks
    private CustomerController customerController;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this); // initializes controller and mocks
        customerController.setCustomerFormValidator(new CustomerFormValidator());
        customerController.setCustomerToCustomerForm(new CustomerToCustomerForm());

        // Note: the customerController will be "mocked" here so it can be found in the tests.
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void testList() throws Exception {

        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer());
        customers.add(new Customer());

        // specific Mockito interaction, tell stub to return list of products
        when(customerService.listAll()).thenReturn((List) customers);
        // Note: need to strip generics to keep Mockito happy.

        // URL mapping #1 - Spring should find listCustomers() in the controller
        mockMvc.perform(get("/customer/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer/list"))
                .andExpect(model().attribute("customers", hasSize(2)));

        // URL mapping #2 - Spring should find listCustomers() in the controller
        mockMvc.perform(get("/customer/"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer/list"))
                .andExpect(model().attribute("customers", hasSize(2)));
    }

    @Test
    public void testShow() throws Exception {

        Integer id = 1;

        // Mockito stub to return new product for ID 1
        when(customerService.getById(id)).thenReturn(new Customer());

        mockMvc.perform(get("/customer/show/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer/show"))
                .andExpect(model().attribute("customer", instanceOf(Customer.class)));
    }

    @Test
    public void testEdit() throws Exception {

        Integer id = 1;

        User user = new User();
        Customer customer = new Customer();
        customer.setUser(user);

        // Mockito stub to return new product for ID 1
        // Customer -> CustomerForm? FAILS WITH ERROR not suitable method found <-- revised 4/12/2017
        when(customerService.getById(id)).thenReturn(customer);
        mockMvc.perform(get("/customer/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer/customerform"))
                .andExpect(model().attribute("customerForm", instanceOf(CustomerForm.class)));
        // Customer -> CustomerForm? <-- revised 4/12/2017
    }

    @Test
    public void testNewCustomer() throws Exception {

        //Integer id = 1;

        // should not call service
        verifyZeroInteractions(customerService);

        mockMvc.perform(get("/customer/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer/customerform"))
                .andExpect(model().attribute("customerForm", instanceOf(CustomerForm.class)));
    }

    @Test
    public void testSaveOrUpdate() throws Exception {

        Integer id = 1;
        String firstName = "Don";
        String lastName = "Smallidge";
        String addressLine1 = "25 Eustis Parkway";
        String addressLine2 = "Apt 2";
        String city = "Waterville";
        String state = "ME";
        String zipCode = "04901";
        String email = "donaldsmallidge@mac.com";
        String phoneNumber = "207.861.1272";
        String username = "fakename";
        String password = "fakepassword";

        Customer returnCustomer = new Customer();
        //CustomerForm returnCustomer = new CustomerForm();
        returnCustomer.setId(id);
        returnCustomer.setFirstName(firstName);
        returnCustomer.setLastName(lastName);
        // Question: billing or shipping address? <-- shipping! [sfg has billing] <-- revised 4/12/2017
        returnCustomer.setBillingAddress(new Address()); // refactor to use embedded Address object
        returnCustomer.getBillingAddress().setAddressLine1(addressLine1);
        returnCustomer.getBillingAddress().setAddressLine2(addressLine2);
        returnCustomer.getBillingAddress().setCity(city);
        returnCustomer.getBillingAddress().setState(state);
        returnCustomer.getBillingAddress().setZipCode(zipCode);
        returnCustomer.setEmail(email);
        returnCustomer.setPhoneNumber(phoneNumber);
        returnCustomer.setUser(new User());
        returnCustomer.getUser().setUsername(username);
        returnCustomer.getUser().setPassword(password);

        /*
        returnCustomer.setAddressLine1(addressLine1);
        returnCustomer.setAddressLine2(addressLine2);
        returnCustomer.setCity(city);
        returnCustomer.setState(state);
        returnCustomer.setZipCode(zipCode);
        */

        // Mockito stub to return new product for ID 1
        // Note: I had to specify org.mockito instead of Hamcrest.
        // Note: sfg redirect:customer/show/1, not redirect:/customer.
        // Question: How handle testing embedded values for billingAddress? <-- shipping!
        // Answer: use dot notation for param (e.g., addressLine1 becomes shippingAddress.addressLine1)
        // Answer: use nested notation for hasProperty (e.g., addressLine1 becomes a property of shippingAddress property)
        when(customerService.saveOrUpdateCustomerForm(org.mockito.Matchers.<CustomerForm>any())).thenReturn(returnCustomer);
        when(customerService.getById(org.mockito.Matchers.<Integer>any())).thenReturn(returnCustomer);

        mockMvc.perform(post("/customer")
                .param("customerId", "1")
                .param("firstName", firstName)
                .param("lastName", lastName)
                .param("userName", username)
                .param("passwordText", password)
                .param("passwordTextConf", password)
                .param("shippingAddress.addressLine1", addressLine1)
                .param("shippingAddress.addressLine2", addressLine2)
                .param("shippingAddress.city", city)
                .param("shippingAddress.state", state)
                .param("shippingAddress.zipCode", zipCode)
                .param("email", email)
                .param("phoneNumber", phoneNumber))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:customer/show/1")); // <-- revised 4/12/2017
//                    .andExpect(model().attribute("customer", instanceOf(Customer.class)))
//                    .andExpect(model().attribute("customer", hasProperty("firstName", is(firstName))))
//                    .andExpect(model().attribute("customer", hasProperty("lastName", is(lastName))))
//                    .andExpect(model().attribute("customer", hasProperty("email", is(email))))
//                    .andExpect(model().attribute("customer", hasProperty("phoneNumber", is(phoneNumber))))
//                    .andExpect(model().attribute("customer", hasProperty("shippingAddress", hasProperty("addressLine1", is(addressLine1)))))
//                    .andExpect(model().attribute("customer", hasProperty("shippingAddress", hasProperty("addressLine2", is(addressLine2)))))
//                    .andExpect(model().attribute("customer", hasProperty("shippingAddress", hasProperty("city", is(city)))))
//                    .andExpect(model().attribute("customer", hasProperty("shippingAddress", hasProperty("state", is(state)))))
//                    .andExpect(model().attribute("customer", hasProperty("shippingAddress", hasProperty("zipCode", is(zipCode)))));

        // verify properties of bound object
        //ArgumentCaptor<Customer> customerCaptor = ArgumentCaptor.forClass(Customer.class);
        ArgumentCaptor<CustomerForm> customerCaptor = ArgumentCaptor.forClass(CustomerForm.class); // <-- revised 4/12/2017
        verify(customerService).saveOrUpdateCustomerForm(customerCaptor.capture()); // <-- revised 4/12/2017

        CustomerForm boundCustomer = customerCaptor.getValue(); // <-- revised 4/12/2017

        assertEquals(id, boundCustomer.getCustomerId()); // <-- revised 4/12/2017
        assertEquals(firstName, boundCustomer.getFirstName());
        assertEquals(lastName, boundCustomer.getLastName());
        assertEquals(email, boundCustomer.getEmail());
        assertEquals(phoneNumber, boundCustomer.getPhoneNumber());

        // Question: billing or shipping address? <-- shipping!
//        assertEquals(addressLine1, boundCustomer.getShippingAddress().getAddressLine1());
//        assertEquals(addressLine2, boundCustomer.getShippingAddress().getAddressLine2());
//        assertEquals(city, boundCustomer.getShippingAddress().getCity());
//        assertEquals(state, boundCustomer.getShippingAddress().getState());
//        assertEquals(zipCode, boundCustomer.getShippingAddress().getZipCode());
        /* NOTE: My Mistake: leftover cloned boundProduct instead of boundCustomer!!!
        assertEquals(addressLine1, boundProduct.getValue().getAddressLine1());
        assertEquals(addressLine2, boundProduct.getValue().getAddressLine2());
        assertEquals(city, boundProduct.getValue().getCity());
        assertEquals(state, boundProduct.getValue().getState());
        assertEquals(zipCode, boundProduct.getValue().getZipCode());
        */
    }

    @Test
    public void testDelete() throws Exception {

        Integer id = 1;

        // Mockito stub to return new product for ID 1
        when(customerService.getById(id)).thenReturn(new Customer());

        mockMvc.perform(get("/customer/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/customer/list"));

        verify(customerService, times(1)).delete(id);
    }

}
