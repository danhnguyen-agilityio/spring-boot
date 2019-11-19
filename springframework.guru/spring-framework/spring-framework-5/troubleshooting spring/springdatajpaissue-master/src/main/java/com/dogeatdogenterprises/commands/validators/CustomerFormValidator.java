package com.dogeatdogenterprises.commands.validators;

import com.dogeatdogenterprises.commands.CustomerForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by donaldsmallidge on 3/14/17.
 */
@Component
public class CustomerFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return CustomerForm.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {

        CustomerForm customerForm = (CustomerForm)target;

        if (!customerForm.getPasswordText().equals(customerForm.getPasswordTextConf())) {
            errors.rejectValue("passwordText", "PasswordsDontMatch.customerForm.passwordText", "Passwords Don't Match");
            errors.rejectValue("passwordTextConf", "PasswordsDontMatch.customerForm.passwordTextConf", "Passwords Don't Match");
        }
    }
}
