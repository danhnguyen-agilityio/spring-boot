package guru.springframework.services;

public class HelloWorldFactory {

    public HelloWorldService createHelloWorldService(String language) {
        switch (language) {
            case "en":
                return new HelloWorldServiceEnglishImpl();
            case "sp":
                return new HelloWorldServiceSpanishImpl();
            case "ge":
                return new HelloWorldServiceGermanImpl();
            case "fr":
                return new HelloWorldServiceFrenchImpl();
            default:
                return new HelloWorldServiceEnglishImpl();
        }
    }
}
