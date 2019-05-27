package com.agility.usermanagement.utils;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit test class ConvertUtil
 */
public class ConvertUtilTest {

    class ObjectDemo {

        String firstName;
        String lastName;

        public ObjectDemo() {
        }

        public ObjectDemo(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
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
    }

    private Object object;
    private Object objectNullLastName;

    @Before
    public void setUp() {
        object = new ObjectDemo("david", "nguyen");
        objectNullLastName = new ObjectDemo("david", null);
    }

    @Test
    public void testConvertObjectToJsonString() throws Exception {
        String jsonObject = ConvertUtil.convertObjectToJsonString(object);

        assertEquals("{\"firstName\":\"david\",\"lastName\":\"nguyen\"}", jsonObject);
    }


    @Test
    public void testConvertObjectToJsonStringWhenObjectHasNullProperty() throws Exception {
        String jsonObject = ConvertUtil.convertObjectToJsonString(objectNullLastName);

        assertEquals("{\"firstName\":\"david\"}", jsonObject);
    }

    @Test
    public void convertObjectToJsonBytes() throws Exception {
        byte[] jsonBytes = ConvertUtil.convertObjectToJsonBytes(object);

        String jsonString = "";

        for (int i = 0; i < jsonBytes.length; i++) {
            jsonString += (char) jsonBytes[i];
        }

        assertEquals("{\"firstName\":\"david\",\"lastName\":\"nguyen\"}", jsonString);
    }

    @Test
    public void convertObjectToJsonBytesWhenObjectHasNullProperty() throws Exception {
        byte[] jsonBytes = ConvertUtil.convertObjectToJsonBytes(objectNullLastName);

        String jsonString = "";

        for (int i = 0; i < jsonBytes.length; i++) {
            jsonString += (char) jsonBytes[i];
        }

        assertEquals("{\"firstName\":\"david\"}", jsonString);
    }
}