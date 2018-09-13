package com.agility.shopping.cart.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.agility.shopping.cart.configs.DateTimeConfig.DATETIME_FORMAT;
import static java.time.format.DateTimeFormatter.ofPattern;

/**
 * Definitions of serializer for all LocalDate class
 */
@Slf4j
public class LocalDateSerializer extends JsonSerializer<LocalDate> {

    private DateTimeFormatter formatter = ofPattern(DATETIME_FORMAT);

    @Override
    public void serialize(LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
        throws IOException, JsonProcessingException {

        jsonGenerator.writeString(localDate.format(formatter));
    }
}
