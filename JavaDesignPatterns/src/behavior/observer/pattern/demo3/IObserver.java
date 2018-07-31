package behavior.observer.pattern.demo3;

public interface IObserver {
  void update(String s, int i);
}

class Observer1 implements IObserver {
  @Override
  public void update(String s, int i) {
    System.out.println("Observer1: " + s + " is now: " + i);
  }
}

class Observer2 implements IObserver {
  @Override
  public void update(String s, int i) {
    System.out.println("Observer2: " + s + " is now: " + i);
  }
}

class Observer3 implements IObserver {
  @Override
  public void update(String s, int i) {
    System.out.println("Observer3: " + s + " is now: " + i);
  }
}