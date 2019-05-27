package structure.decorator.pattern.demo;

public class ConcreteDecoratorEx_1 extends AbstractDecorator {
  public void doJob() {
    super.doJob();
    // Add additional if necessary
    System.out.println("Explicitly from EX1");
  }
}
