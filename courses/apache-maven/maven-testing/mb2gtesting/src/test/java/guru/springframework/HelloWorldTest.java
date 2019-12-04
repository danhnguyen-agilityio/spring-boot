package guru.springframework;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelloWorldTest {

    @Test
    void hello() {
        HelloWorld helloWorld = new HelloWorld();

        assertEquals("Hello world", helloWorld.hello());
    }
}