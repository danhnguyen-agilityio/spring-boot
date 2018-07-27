package decorator.pattern.demo;

public class DecoratorPatternEx {
  public static void main(String[] args) {
    ConcreteComponent cc = new ConcreteComponent();

    ConcreteDecoratorEx_1 cd1 = new ConcreteDecoratorEx_1();

    // Decorating ConcreteComponent object with ConcreteDecoratorEx_1
    cd1.setTheComponent(cc);
    cd1.doJob();

    ConcreteDecoratorEx_2 cd2 = new ConcreteDecoratorEx_2();

    // Decorating ConcreteComponent object with ConcreteDecoratorEx_2
    cd2.setTheComponent(cc);
    cd2.doJob();
  }
}
