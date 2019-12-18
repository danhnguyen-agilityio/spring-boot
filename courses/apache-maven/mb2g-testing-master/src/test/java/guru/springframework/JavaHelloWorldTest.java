package guru.springframework;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JavaHelloWorldTest {

    @Test
    void hello() {
        System.out.println("====================");
        System.out.println(System.getenv("TEST_HOST"));
    }
}