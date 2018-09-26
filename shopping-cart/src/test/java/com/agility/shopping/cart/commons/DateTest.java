package com.agility.shopping.cart.commons;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import org.junit.Test;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.TimeZone;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class DateTest {

    class Event implements Serializable {
        String name;
        Date date;

        public Event(String name, Date date) {
            this.name = name;
            this.date = date;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }
    }

    class OtherEvent {
        String name;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
        Date date;

        public OtherEvent(String name, Date date) {
            this.name = name;
            this.date = date;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }
    }

    class NewEvent {
        String name;

        @JsonSerialize(using = CustomDateSerializer.class)
        Date date;

        public NewEvent(String name, Date date) {
            this.name = name;
            this.date = date;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }
    }

    /**
     * Test serialize Date to Timestamp (number of milliseconds since January 1st, 1970, UTC).
     */
    @Test
    public void shouldSerializeToTimestampWhenSerializingDateWithJackson() throws ParseException, JsonProcessingException {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date date = df.parse("01-01-1970 01:00");
        Event event = new Event("party", date);

        ObjectMapper objectMapper = new ObjectMapper();
        String result = objectMapper.writeValueAsString(event);

        assertThat(result, containsString("party"));
        assertThat(result, containsString("3600000"));
    }


    /**
     * Serialize Date to ISO-8601
     */
    @Test
    public void shouldSerializeToTextWhenSerializingDateToISO8601() throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));

        String toParse = "01-01-1970 02:30";
        Date date = df.parse(toParse);
        Event event = new Event("party", date);

        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.setDateFormat(new ISO8601DateFormat());
        String result = mapper.writeValueAsString(event);

        assertThat(result, containsString("1970-01-01T02:30:00Z"));
    }

    /**
     * Set our formats for representing dates
     */
    @Test
    public void shouldCorrectWhenSettingObjectMapperDateFormat() throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm");

        String toParse = "20-12-2014 02:30";
        Date date = df.parse(toParse );
        Event event = new Event("party", date);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(df);

        String result = mapper.writeValueAsString(event);
        assertThat(result, containsString(toParse));
    }

    /**
     * Test use @JsonFormat to format Date
     */
    @Test
    public void shouldCorrectWhenUsingJsonFormatAnnotationToFormatDate() throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));

        String toPare = "20-12-2014 02:30:00";
        Date date = df.parse(toPare );
        OtherEvent event = new OtherEvent("party", date);

        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writeValueAsString(event);

        assertThat(result, containsString(toPare));
    }

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
