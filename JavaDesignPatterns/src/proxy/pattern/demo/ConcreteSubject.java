package proxy.pattern.demo;

public class ConcreteSubject extends Subject {
  @Override
  public void doSomeWork() {
    System.out.println("Concrete subject");
  }
}
