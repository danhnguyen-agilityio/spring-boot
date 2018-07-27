package observer.pattern.demo;

public class Observer1 implements IObserver {
  @Override
  public void update(int i) {
    System.out.println("Observer1: " + i);
  }
}
