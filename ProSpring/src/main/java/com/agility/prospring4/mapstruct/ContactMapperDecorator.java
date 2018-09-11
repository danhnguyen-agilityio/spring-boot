package com.agility.prospring4.mapstruct;

public abstract class ContactMapperDecorator implements ContactMapper{

    private final ContactMapper delegate;

    public ContactMapperDecorator(ContactMapper delegate) {
        this.delegate = delegate;
    }

    @Override
    public BusinessContact primaryToBusinessContact(PrimaryContact primary){
        BusinessContact business = delegate.primaryToBusinessContact(primary); //Executes the mapper
        String[] names = primary.getName().split(" ");
        business.setFirstName(names[0]);
        business.setLastName(names[1]);
        return business;
    }

    @Override
    public PrimaryContact businessToPrimaryContact(BusinessContact business){
        PrimaryContact primary = delegate.businessToPrimaryContact(business); //Executes the mapper
        primary.setName(business.getFirstName() + " " + business.getLastName());
        return primary;
    }

}
