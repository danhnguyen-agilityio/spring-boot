package guru.springframework;

public class HelloWorld {

    public String hello() {
        return "Hello world";
    }

    public String print(int x) {
        if (x > 10) {
            return ">";
        } else {
            return "<";
        }
    }
}
