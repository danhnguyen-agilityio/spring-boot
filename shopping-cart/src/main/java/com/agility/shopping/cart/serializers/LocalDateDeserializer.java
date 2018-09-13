package com.agility.shopping.cart.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.agility.shopping.cart.configs.DateTimeConfig.DATETIME_FORMAT;
import static java.time.format.DateTimeFormatter.ofPattern;

/**
 * Definitions of deserializer for all LocalDate class
 */
public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

    private DateTimeFormatter formatter = ofPattern(DATETIME_FORMAT);

    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
        throws IOException, JsonProcessingException {

        return LocalDate.parse(jsonParser.getText(), formatter);
    }
}
