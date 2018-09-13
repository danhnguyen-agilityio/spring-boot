package com.agility.shopping.cart.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import static com.agility.shopping.cart.configs.DateTimeConfig.DATETIME_FORMAT;
import static java.time.format.DateTimeFormatter.ofPattern;

/**
 * Definitions of deserializer for all Instant classes
 */
public class InstantDeserializer extends JsonDeserializer<Instant> {

    private DateTimeFormatter formatter = ofPattern(DATETIME_FORMAT).withZone(ZoneOffset.UTC);

    @Override
    public Instant deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
        throws IOException, JsonProcessingException {

        return Instant.from(formatter.parse(jsonParser.getText()));
    }
}
