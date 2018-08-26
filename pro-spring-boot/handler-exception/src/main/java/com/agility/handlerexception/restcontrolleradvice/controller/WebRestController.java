package com.agility.handlerexception.restcontrolleradvice.controller;

import com.agility.handlerexception.restcontrolleradvice.exception.BadRequestException;
import com.agility.handlerexception.restcontrolleradvice.exception.NotFoundException;
import com.agility.handlerexception.restcontrolleradvice.model.CustomError;
import com.agility.handlerexception.restcontrolleradvice.model.Customer;
import com.agility.handlerexception.restcontrolleradvice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.Valid;

@RestController
public class WebRestController {

    @Autowired
    CustomerService customerService;

    @RequestMapping(value = "/customer/{name}")
    public Customer findCustomerByName(@PathVariable("name") String name) {

        if ("Illegal".equals(name)) {
            throw new IllegalArgumentException("Illegal argument request");
        }

        if ("Mismatch".equals(name)) {
            throw new MethodArgumentTypeMismatchException(null, null, null, null, null);
        }

        if ("Danh".equals(name)) {
            throw new BadRequestException(CustomError.BAD_REQUEST);
        }

        Customer cust = customerService.findCustomerByName(name);

        if (null == cust) {
            throw new NotFoundException(CustomError.USER_NOT_FOUND);
        }

        return cust;
    }

    @RequestMapping(value = "/customer")
    public Customer findCustomerById(@RequestParam("id") int id) {
        Customer cust = customerService.findCustomerByName("Jack");
        return cust;
    }

    @RequestMapping(value = "/invalid", method = RequestMethod.POST)
    public Customer invalidRequestBody(@Valid @RequestBody Customer customer) {
        return customer;
    }


}
