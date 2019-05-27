package com.agility.prospring4.mapstruct;

public class BusinessContact {

    private String firstName;
    private String lastName;
    private String businessPhone;
    private String businessEmail;
    private String businessCountry;

    public BusinessContact() {
        super();
    }

    public BusinessContact(String firstName, String lastName, String businessPhone, String businessEmail,
                           String businessCountry) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.businessPhone = businessPhone;
        this.businessEmail = businessEmail;
        this.businessCountry = businessCountry;
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

    public String getBusinessPhone() {
        return businessPhone;
    }

    public void setBusinessPhone(String businessPhone) {
        this.businessPhone = businessPhone;
    }

    public String getBusinessEmail() {
        return businessEmail;
    }

    public void setBusinessEmail(String businessEmail) {
        this.businessEmail = businessEmail;
    }

    public String getBusinessCountry() {
        return businessCountry;
    }

    public void setBusinessCountry(String businessCountry) {
        this.businessCountry = businessCountry;
    }

    @Override
    public String toString() {
        return "BusinessContact [firstName=" + firstName + ", lastName=" + lastName + ", businessPhone=" + businessPhone
            + ", businessEmail=" + businessEmail + ", businessCountry=" + businessCountry + "]";
    }

}