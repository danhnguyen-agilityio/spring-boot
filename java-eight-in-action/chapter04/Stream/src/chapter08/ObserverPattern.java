package chapter08;

import java.util.ArrayList;
import java.util.List;

interface Observer {
  void notify(String tweet);
}

class NYTimes implements Observer {
  public void notify(String tweet) {
    System.out.println("NYTimes");
  }
}

class Guardian implements Observer {

  @Override
  public void notify(String tweet) {
    System.out.println("Guardian");
  }
}

class LeMonde implements Observer {

  @Override
  public void notify(String tweet) {
    System.out.println("LeMonde");
  }
}

interface Subject {
  void registerObserver(Observer o);
  void notifyObservers(String tweet);
}

class Feed implements Subject {
  private final List<Observer> observers = new ArrayList<>();

  public void registerObserver(Observer o) {
    this.observers.add(o);
  }

  public void notifyObservers(String tweet) {
    for (Observer observer : observers) {
      observer.notify(tweet);
    }
  }
}

public class ObserverPattern {

  public static void main(String[] args) {
    Feed f = new Feed();
    f.registerObserver(new NYTimes());
    f.registerObserver(new Guardian());
    f.registerObserver(new LeMonde());
    f.notifyObservers("Action");
  }
}
