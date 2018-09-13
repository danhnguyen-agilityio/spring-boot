package com.agility.shopping.cart.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import static com.agility.shopping.cart.configs.DateTimeConfig.DATETIME_FORMAT;
import static java.time.format.DateTimeFormatter.ofPattern;

/**
 * Definitions of serializer for all Instant class
 */
public class InstantSerializer extends JsonSerializer<Instant> {

    private DateTimeFormatter formatter = ofPattern(DATETIME_FORMAT).withZone(ZoneOffset.UTC);

    @Override
    public void serialize(Instant value, JsonGenerator gen, SerializerProvider serializerProvider)
        throws IOException, JsonProcessingException {

        gen.writeString(formatter.format(value));
    }
}
