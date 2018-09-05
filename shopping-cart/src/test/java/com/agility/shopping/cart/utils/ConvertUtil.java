package com.agility.shopping.cart.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * ConvertUtil class convert old value to new value that we want
 */
public class ConvertUtil {

    /**
     * Convert object to bytes that contains the JSON representation of the object
     *
     * @param object Object need convert
     * @return Byte array that converted from object
     * @throws JsonProcessingException
     */
    public static byte[] convertObjectToJsonBytes(Object object)
        throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        // Configure the created object to include only non null properties
        // of serialized object
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // Convert the object as json string and return the created string
        // as byte array
        return mapper.writeValueAsBytes(object);
    }
}
