package com.agility.prospring4.mapstruct;

public class ContactConvertor {

    public static void main(String[] args) {
        PrimaryContact primary = new PrimaryContact("Jack Sparrow","9999999999","test@javareferencegv.com");
        BusinessContact business = ContactMapper.INSTANCE.primaryToBusinessContact(primary);
        System.out.println(business);
        PrimaryContact primaryConverted = ContactMapper.INSTANCE.businessToPrimaryContact(business);
        System.out.println(primaryConverted);
    }

}