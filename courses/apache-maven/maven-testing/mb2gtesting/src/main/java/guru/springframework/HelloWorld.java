package guru.springframework;

public class HelloWorld {

    String x;

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
