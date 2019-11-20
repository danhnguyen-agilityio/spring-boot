package com.dogeatdogenterprises.config;

import com.dogeatdogenterprises.converters.CustomerFormToCustomer;
import com.dogeatdogenterprises.converters.ProductFormToProduct;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by donaldsmallidge on 1/8/17.
 */
@Configuration
@EnableJpaRepositories("com.dogeatdogenterprises.repositories")
public class CommonBeanConfig {

    @Bean
    public StrongPasswordEncryptor strongEncryptor() {
        StrongPasswordEncryptor encryptor = new StrongPasswordEncryptor();
        return encryptor;
    }

    // HIS VERSION DOES NOT HAVE THESE BEANS DEFINED
    @Bean
    public CustomerFormToCustomer customerFormToCustomer() {
        CustomerFormToCustomer customerFormToCustomer = new CustomerFormToCustomer();
        return customerFormToCustomer;
    }

    @Bean
    public ProductFormToProduct productFormToProduct() {
        ProductFormToProduct productFormToProduct = new ProductFormToProduct();
        return productFormToProduct;
    }
}
