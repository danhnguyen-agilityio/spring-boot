package com.agility.shopping.cart.commons;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class DateTest {

    @Test
    public void shouldCorrectWhenSerializingJava8Date() throws JsonProcessingException {
        LocalDateTime dateTime = LocalDateTime.of(2014, 12, 20, 2, 30, 11);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JSR310Module());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String result = mapper.writeValueAsString(dateTime);
        assertThat(result, containsString("2014-12-20T02:30"));
    }
}
