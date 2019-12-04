package guru.springframework;

import org.junit.Test;

import static org.junit.Assert.*;

public class HelloWorldTest {

    @Test
    public void hello() {
        HelloWorld helloWorld = new HelloWorld();
        assertEquals("Hello world", helloWorld.hello());
    }
}