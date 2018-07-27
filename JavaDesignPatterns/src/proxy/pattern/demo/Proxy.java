package proxy.pattern.demo;

public class Proxy extends Subject {
  ConcreteSubject cs;

  @Override
  public void doSomeWork() {
    System.out.println("Proxy call happening now");
    // Lazy initialization
    if (cs == null) {
      cs = new ConcreteSubject();
    }
    cs.doSomeWork();
  }
}
