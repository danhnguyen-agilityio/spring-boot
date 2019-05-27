package com.agility.mapstruct.mapper;

import com.agility.mapstruct.models.BusinessContact;
import com.agility.mapstruct.models.PrimaryContact;
import org.junit.Test;

import static org.junit.Assert.*;

public class ContactMapperTest {

    @Test
    public void testPrimaryToBusinessContact() {
        PrimaryContact primary = new PrimaryContact("Jack David",
            "999999", "test@gmail.com");

        BusinessContact business =
            ContactMapper.INSTANCE.primaryToBusinessContact(primary);

        assertEquals(primary.getName(), business.getFirstName() + " " + business.getLastName());
    }

}