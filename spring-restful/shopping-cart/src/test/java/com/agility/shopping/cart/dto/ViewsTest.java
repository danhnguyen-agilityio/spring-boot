package com.agility.shopping.cart.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

@Slf4j
public class ViewsTest {

    /**
     * Should serialize only member when use member view
     */
    @Test
    public void shouldSerializeOnlyMemberWhenUseMemberView() throws JsonProcessingException {
        log.debug("Should serialized only member when use member view");

        Item item = new Item(2, "book", "John", "Description");

        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writerWithView(Views.Member.class).writeValueAsString(item);
        log.debug("Result json string: {}", result);

        assertThat(result, containsString("book"));
        assertThat(result, containsString("2"));
        assertThat(result, not(Matchers.containsString("John")));
        assertThat(result, containsString("Description"));
    }

    /**
     * Should serialize all when use admin view
     */
    @Test
    public void shouldSerializeAllWhenUseAdminView() throws JsonProcessingException {
        log.debug("Should serialized all when use admin view");
        Item item = new Item(2, "book", "John", "Description");

        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writerWithView(Views.Admin.class).writeValueAsString(item);
        log.debug("Result json string: {}", result);

        assertThat(result, containsString("book"));
        assertThat(result, containsString("2"));
        assertThat(result, containsString("John"));
        assertThat(result, containsString("Description"));
    }

    /**
     * Should deserialize only member when use member view
     */
    @Test
    public void shouldDeserializeOnlyMemberWhenUseMemberView() throws IOException {
        String json = "{\"id\":2,\"name\":\"book\",\"owner\":\"John\",\"description\":\"Description\"}";

        ObjectMapper mapper = new ObjectMapper();
        Item item = mapper.readerWithView(Views.Member.class)
            .forType(Item.class)
            .readValue(json);

        assertEquals(2, item.getId());
        assertEquals("book", item.getName());
        assertEquals(null, item.getOwner());
        assertEquals("Description", item.getDescription());
    }

    /**
     * Should deserialize all when use admin view
     */
    @Test
    public void shouldDeserializeAllWhenUseAdminView() throws IOException {
        String json = "{\"id\":2,\"name\":\"book\",\"owner\":\"John\",\"description\":\"Description\"}";

        ObjectMapper mapper = new ObjectMapper();
        Item item = mapper.readerWithView(Views.Admin.class)
            .forType(Item.class)
            .readValue(json);

        assertEquals(2, item.getId());
        assertEquals("book", item.getName());
        assertEquals("John", item.getOwner());
        assertEquals("Description", item.getDescription());
    }
}
