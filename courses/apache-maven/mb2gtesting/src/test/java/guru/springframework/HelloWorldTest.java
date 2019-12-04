package guru.springframework;


public class HelloWorldTest {

    // Surefire plugin is gonna pick up anything that begins or ends with test
    // This is the default behaviour of Maven
    // It gonna be looking for things methods that begin with test
    // Example: HelloWorldTest,testHello
    public void testHello() {
        HelloWorld helloWorld = new HelloWorld();
        assert("Hello world".equals(helloWorld.hello()));
    }
}
