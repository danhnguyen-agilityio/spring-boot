package springframework.guru.didemo.controlller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import springframework.guru.didemo.services.GreetingService;

@Component
public class MyController {

    @Autowired
    private GreetingService greetingService;

    public String hello() {
        System.out.println("Hello world");
        return greetingService.sayGreeting();
    }
}
