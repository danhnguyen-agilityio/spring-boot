package com.agility.mapstruct.mapper;

import com.agility.mapstruct.models.BusinessContact;
import com.agility.mapstruct.models.PrimaryContact;

public abstract class ContactMapperDecorator implements ContactMapper {
    private final ContactMapper delegate;

    public ContactMapperDecorator(ContactMapper delegate) {
        this.delegate = delegate;
    }

    @Override
    public BusinessContact primaryToBusinessContact(PrimaryContact primary) {
        BusinessContact business = delegate.primaryToBusinessContact(primary);
        String[] names =  primary.getName().split(" ");
        business.setFirstName(names[0]);
        business.setLastName(names[1]);
        return business;
    }

    @Override
    public PrimaryContact businesstoPrimaryContact(BusinessContact business) {
        PrimaryContact primary = delegate.businesstoPrimaryContact(business);
        primary.setName(business.getFirstName() + " " + business.getLastName());
        return primary;
    }
}
