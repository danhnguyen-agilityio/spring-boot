package behavior.observer.pattern.demo;

public class Observer2 implements IObserver {
  @Override
  public void update(int i) {
    System.out.println("Observer2: " + i);
  }
}
