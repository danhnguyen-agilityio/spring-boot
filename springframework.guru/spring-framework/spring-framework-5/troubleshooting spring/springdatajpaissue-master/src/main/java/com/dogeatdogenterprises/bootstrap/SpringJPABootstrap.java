package com.dogeatdogenterprises.bootstrap;

import com.dogeatdogenterprises.domain.*;
import com.dogeatdogenterprises.domain.security.Role;
import com.dogeatdogenterprises.enums.OrderStatus;
import com.dogeatdogenterprises.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by donaldsmallidge on 12/6/16. supersedes loadDomainObjects in mapservices
 */
@Component
public class SpringJPABootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private ProductService productService;
    private UserService userService;
    private RoleService roleService;


    @Autowired
    public void setProductService(ProductService productService) {

        this.productService = productService;
    }

    @Autowired
    public void setUserService(UserService userService) {

        this.userService = userService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {

        this.roleService = roleService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        loadProducts();
        loadUsersAndCustomers();
        loadCarts();
        loadOrderHistory();
        loadRoles();
        assignUsersToDefaultRole();
        assignUsersToAdminRole();
    }

    private void assignUsersToDefaultRole() {
        List<Role> roles = (List<Role>) roleService.listAll();
        List<User> users = (List<User>) userService.listAll();

        roles.forEach(role -> {
            if (role.getRole().equalsIgnoreCase("CUSTOMER")) {
                users.forEach(user -> {
                    user.addRole(role);
                    userService.saveOrUpdate(user);
                });
            }
        });
    }

    private void assignUsersToAdminRole() {
        List<Role> roles = (List<Role>) roleService.listAll();
        List<User> users = (List<User>) userService.listAll();

        roles.forEach(role -> {
            if (role.getRole().equalsIgnoreCase("ADMIN")) {
                users.forEach(user -> {
                    if (user.getUsername().equalsIgnoreCase("bschuster")) {
                        user.addRole(role);
                        userService.saveOrUpdate(user);
                    }
                });
            }
        });
    }

    private void loadRoles() {

        Role role = new Role();
        role.setRole("CUSTOMER");
        roleService.saveOrUpdate(role);

        Role adminRole = new Role();
        adminRole.setRole("ADMIN");
        roleService.saveOrUpdate(adminRole);
    }

    private void loadOrderHistory() {
        List<User> users = (List<User>) userService.listAll();
        List<Product> products = (List<Product>) productService.listAll();

        users.forEach(user -> {
            Order order = new Order();
            order.setCustomer(user.getCustomer());
            order.setOrderStatus(OrderStatus.SHIPPED);

            products.forEach(product -> {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setProduct(product);
                orderDetail.setQuantity(1);
                order.addToOrderDetails(orderDetail);
            });
        });

    }

    private void loadCarts() {
        List<User> users = (List<User>) userService.listAll();
        List<Product> products = (List<Product>) productService.listAll();

        users.forEach(user -> {
            user.setCart(new Cart());
            CartDetail cartDetail = new CartDetail();
            cartDetail.setProduct(products.get(0));
            cartDetail.setQuantity(2);
            user.getCart().addCartDetail(cartDetail);
            userService.saveOrUpdate(user);
        });

    }

    public void loadUsersAndCustomers() {
        User user1 = new User();
        user1.setUsername("anewman");
        user1.setPassword("password");

        Customer customer1 = new Customer();
        //customer1.setId(1);
        customer1.setFirstName("Alfred");
        customer1.setLastName("Newman");
        customer1.setBillingAddress(new Address()); // refactor to use embedded Address object
        customer1.getBillingAddress().setAddressLine1("1 Alfred Lane");
        customer1.getBillingAddress().setAddressLine2("Apt. 101");
        customer1.getBillingAddress().setCity("Alfred");
        customer1.getBillingAddress().setState("Maine");
        customer1.getBillingAddress().setZipCode("04291");
        customer1.setEmail("alfred@gmail.com");
        customer1.setPhoneNumber("207-101-1001");
        user1.setCustomer(customer1);
        userService.saveOrUpdate(user1);

        User user2 = new User();
        user2.setUsername("bschuster");
        user2.setPassword("password");

        Customer customer2 = new Customer();
        //customer2.setId(2);
        customer2.setFirstName("Brad");
        customer2.setLastName("Schuster");
        customer2.setBillingAddress(new Address()); // refactor to use embedded Address object
        customer2.getBillingAddress().setAddressLine1("2 Bradford Street");
        customer2.getBillingAddress().setAddressLine2("Apt. 202");
        customer2.getBillingAddress().setCity("Waterville");
        customer2.getBillingAddress().setState("Maine");
        customer2.getBillingAddress().setZipCode("04902");
        customer2.setEmail("bschuster@yahoo.com");
        customer2.setPhoneNumber("207-202-2002");
        user2.setCustomer(customer2);
        userService.saveOrUpdate(user2);

        User user3 = new User();
        user3.setUsername("canthracks");
        user3.setPassword("password");

        Customer customer3 = new Customer();
        //customer3.setId(3);
        customer3.setFirstName("Claire");
        customer3.setLastName("Anthracks");
        customer3.setBillingAddress(new Address()); // refactor to use embedded Address object
        customer3.getBillingAddress().setAddressLine1("3 Claire Avenue");
        customer3.getBillingAddress().setAddressLine2("Apt. 303");
        customer3.getBillingAddress().setCity("Portland");
        customer3.getBillingAddress().setState("Maine");
        customer3.getBillingAddress().setZipCode("04033");
        customer3.setEmail("claireax@ygmail.com");
        customer3.setPhoneNumber("207-302-3102");
        user3.setCustomer(customer3);
        userService.saveOrUpdate(user3);

        User user4 = new User();
        user4.setUsername("dlefleur");
        user4.setPassword("password");

        Customer customer4 = new Customer();
        //customer4.setId(4);
        customer4.setFirstName("Dennis");
        customer4.setLastName("LeFleur");
        customer4.setBillingAddress(new Address()); // refactor to use embedded Address object
        customer4.getBillingAddress().setAddressLine1("4 Dentist Road");
        customer4.getBillingAddress().setAddressLine2("");
        customer4.getBillingAddress().setCity("Bangor");
        customer1.getBillingAddress().setState("Maine");
        customer1.getBillingAddress().setZipCode("04734");
        customer4.setEmail("claireax@ygmail.com");
        customer4.setPhoneNumber("207-402-4000");
        user4.setCustomer(customer4);
        userService.saveOrUpdate(user4);

        User user5 = new User();
        user5.setUsername("efrommage");
        user5.setPassword("password");

        Customer customer5 = new Customer();
        //customer5.setId(5);
        customer5.setFirstName("Enid");
        customer5.setLastName("Frommage");
        customer5.setBillingAddress(new Address()); // refactor to use embedded Address object
        customer5.getBillingAddress().setAddressLine1("5 Cheese Way");
        customer5.getBillingAddress().setAddressLine2("Carriage House Room 3");
        customer5.getBillingAddress().setCity("South Paris");
        customer5.getBillingAddress().setState("Maine");
        customer5.getBillingAddress().setZipCode("04255");
        customer5.setEmail("cheezy@ygmail.com");
        customer5.setPhoneNumber("207-502-5500");
        user5.setCustomer(customer5);
        userService.saveOrUpdate(user5);
    }

    public void loadProducts() {

        Product product1 = new Product();
        product1.setDescription("Product 1");
        product1.setPrice(new BigDecimal("11.99"));
        product1.setImageUrl("http://example.com/product1");
        productService.saveOrUpdate(product1);

        Product product2 = new Product();
        product2.setDescription("Product 2");
        product2.setPrice(new BigDecimal("12.99"));
        product2.setImageUrl("http://example.com/product2");
        productService.saveOrUpdate(product2);

        Product product3 = new Product();
        product3.setDescription("Product 3");
        product3.setPrice(new BigDecimal("13.99"));
        product3.setImageUrl("http://example.com/product3");
        productService.saveOrUpdate(product3);

        Product product4 = new Product();
        product4.setDescription("Product 4");
        product4.setPrice(new BigDecimal("14.99"));
        product4.setImageUrl("http://example.com/product4");
        productService.saveOrUpdate(product4);

        Product product5 = new Product();
        product5.setDescription("Product 5");
        product5.setPrice(new BigDecimal("15.99"));
        product5.setImageUrl("http://example.com/product5");
        productService.saveOrUpdate(product5);
    }

}
