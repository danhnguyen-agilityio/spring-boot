package springframework.guru.didemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import springframework.guru.didemo.controlller.ConstructorInjectedController;
import springframework.guru.didemo.controlller.MyController;
import springframework.guru.didemo.controlller.PropertyInjectedController;
import springframework.guru.didemo.controlller.SetterInjectedController;
import springframework.guru.didemo.examplebeans.FakeDataSource;
import springframework.guru.didemo.examplebeans.FakeJmsBroker;

@SpringBootApplication
public class DiDemoApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(DiDemoApplication.class, args);

        MyController myController = (MyController) context.getBean("myController");

        myController.hello();

        System.out.println(myController.hello());
        System.out.println(((PropertyInjectedController) context.getBean("propertyInjectedController")).sayHello());
        System.out.println(((SetterInjectedController) context.getBean("setterInjectedController")).sayHello());
        System.out.println(((ConstructorInjectedController) context.getBean("constructorInjectedController")).sayHello());

        FakeDataSource fakeDataSource = context.getBean(FakeDataSource.class);
        System.out.println("Username: " + fakeDataSource.getUsername());

        FakeJmsBroker fakeJmsBroker = context.getBean(FakeJmsBroker.class);
        System.out.printf("Username: " + fakeJmsBroker.getUsername());
    }

}
