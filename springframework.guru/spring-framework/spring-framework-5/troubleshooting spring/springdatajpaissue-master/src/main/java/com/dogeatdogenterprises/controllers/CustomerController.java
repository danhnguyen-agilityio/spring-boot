package com.dogeatdogenterprises.controllers;

import com.dogeatdogenterprises.commands.CustomerForm;
import com.dogeatdogenterprises.commands.validators.CustomerFormValidator;
import com.dogeatdogenterprises.converters.CustomerToCustomerForm;
import com.dogeatdogenterprises.domain.Customer;
import com.dogeatdogenterprises.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by donaldsmallidge on 9/20/16.
 * <p>
 * Create a controller. Implement the ability to
 * list,
 * show one customer,
 * add a customer,
 * update a customer, and
 * delete a customer by id.
 */
@RequestMapping("/customer")
@Controller
public class CustomerController {

    private CustomerService customerService;
    private Validator customerFormValidator;
    private CustomerToCustomerForm customerToCustomerForm;

    @Autowired
    public void setCustomerService(CustomerService customerService) {

        this.customerService = customerService;
    }

    @Autowired
    @Qualifier("customerFormValidator")
    public void setCustomerFormValidator(CustomerFormValidator customerFormValidator) {

        this.customerFormValidator = customerFormValidator;
    }

    @Autowired
    public void setCustomerToCustomerForm(CustomerToCustomerForm customerToCustomerForm) {

        this.customerToCustomerForm = customerToCustomerForm;
    }

    @RequestMapping({"/list", "/"})
    public String listCustomers(Model model) {

        model.addAttribute("customers", customerService.listAll());

        return "customer/list";
    }

    @RequestMapping("/show/{id}")
    public String showCustomer(@PathVariable Integer id, Model model) {

        model.addAttribute("customer", customerService.getById(id));

        return "customer/show";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {

        Customer customer = customerService.getById(id);

        model.addAttribute("customerForm", customerToCustomerForm.convert(customer));

        return "customer/customerform";
    }

    @RequestMapping("/new")
    public String newCustomer(Model model) {
        // Note: This method does NOT use customerService.
        model.addAttribute("customerForm", new CustomerForm());
        return "customer/customerform";
    }

    // Note: removed value = "/", from @RequestMapping - not on GitHub version.
    // Note: revised return value from redirect:/customer/list - t:/c not on GitHub version.
    @RequestMapping(method = RequestMethod.POST)
    public String saveOrUpdate(@Valid CustomerForm customerForm, BindingResult bindingResult) {

        customerFormValidator.validate(customerForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "customer/customerform";
        }
        // Note: This method returns a REDIRECT. And a POST.
        Customer newCustomer = customerService.saveOrUpdateCustomerForm(customerForm);
        return "redirect:customer/show/" + newCustomer.getId();
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        // Note: This method returns a REDIRECT.
        customerService.delete(id);
        return "redirect:/customer/list";
    }

}
