package structure.decorator.pattern.demo;

public class ConcreteDecoratorEx_2 extends AbstractDecorator {
  public void doJob() {
    super.doJob();
    System.out.println("Explicitly from EX2");
  }
}
