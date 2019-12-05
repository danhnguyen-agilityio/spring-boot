package guru.springframework;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelloWorldIT {

    @Test
    public void myPauxIntegeration() {
        System.out.println("My IT Ran");
    }

    @org.junit.jupiter.api.Test
    void print() {
        HelloWorld helloWorld = new HelloWorld();

        assertEquals("Hello world", helloWorld.hello());
        assertEquals(">", helloWorld.print(12));
        assertEquals("<", helloWorld.print(2));
    }
}
