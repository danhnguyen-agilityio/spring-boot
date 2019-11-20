package guru.springframework.services;


public class HelloWorldServiceGermanImpl implements HelloWorldService {
    @Override
    public String getGreeting() {
        return "Bonjour le monde";
    }
}
