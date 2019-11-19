package com.dogeatdogenterprises.domain;

import javax.persistence.*;

/**
 * Created by donaldsmallidge on 9/20/16.
 *
 * 1. first name,
 * last name,
 * email,
 * phone number,
 * address line one,
 * address line two,
 * city,
 * state,
 * zip code.
 * All properties are strings. All should be private and have getters and setters.
 *
 * 2. Add an id value to your customer object.
 * The Id should be an Integer and have a getter and setter.
 */
@Entity
public class Customer extends AbstractDomainClass {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
//    private String addressLine1;
//    private String addressLine2;
//    private String city;
//    private String state;
//    private String zipCode;
/*
    Cf. https://docs.jboss.org/hibernate/orm/5.1/userguide/html_single/chapters/domain/embeddables.html
    but look carefully at examples here instead:
    https://docs.jboss.org/hibernate/jpa/2.1/api/javax/persistence/AttributeOverride.html
 */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "addressLine1",
                    column = @Column( name = "billing_address_line1" )
            ),
            @AttributeOverride(
                    name = "addressLine2",
                    column = @Column( name = "billing_address_line2" )
            ),
            @AttributeOverride(
                    name = "city",
                    column = @Column( name = "billing_city" )
            ),
            @AttributeOverride(
                    name = "state",
                    column = @Column( name = "billing_state" )
            ),
            @AttributeOverride(
                    name = "zipCode",
                    column = @Column( name = "billing_zip_code" )
            )
    })
    private Address billingAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(
                    name = "addressLine1",
                    column = @Column( name = "shipping_address_line1" )
            ),
            @AttributeOverride(
                    name = "addressLine2",
                    column = @Column( name = "shipping_address_line2" )
            ),
            @AttributeOverride(
                    name = "city",
                    column = @Column( name = "shipping_city" )
            ),
            @AttributeOverride(
                    name = "state",
                    column = @Column( name = "shipping_state" )
            ),
            @AttributeOverride(
                    name = "zipCode",
                    column = @Column( name = "shipping_zip_code" )
            )
    })
    private Address shippingAddress;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private User user; // not using CascadeType.ALL requires you to handle deletes manually

    @Override
    public Integer getId() {

        return id;
    }

    public String getFirstName() {

        return firstName;
    }

    public void setFirstName(String firstName) {

        this.firstName = firstName;
    }

    public String getLastName() {

        return lastName;
    }

    public void setLastName(String lastName) {

        this.lastName = lastName;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public String getPhoneNumber() {

        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {

        this.phoneNumber = phoneNumber;
    }
/*
    public String getAddressLine1() {

        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {

        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {

        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {

        this.addressLine2 = addressLine2;
    }

    public String getCity() {

        return city;
    }

    public void setCity(String city) {

        this.city = city;
    }

    public String getState() {

        return state;
    }

    public void setState(String state) {

        this.state = state;
    }

    public String getZipCode() {

        return zipCode;
    }

    public void setZipCode(String zipCode) {

        this.zipCode = zipCode;
    }
*/

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}
