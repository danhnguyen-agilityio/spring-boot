package behavior.observer.pattern.demo3;

import java.util.ArrayList;
import java.util.List;

public interface ISubject {
  void register(IObserver o);
  void unregister(IObserver o);
  void notifyObservers(int i);
}

class Subject1 implements ISubject {
  List<IObserver> observerList = new ArrayList<>();
  private int myValue;

  public int getMyValue() {
    return myValue;
  }

  public void setMyValue(int myValue) {
    this.myValue = myValue;
    notifyObservers(myValue);
  }

  @Override
  public void register(IObserver o) {
    observerList.add(o);
  }

  @Override
  public void unregister(IObserver o) {
    observerList.remove(o);
  }

  @Override
  public void notifyObservers(int updatedValue ) {
    for (IObserver o : observerList) {
      o.update(this.getClass().getSimpleName(), updatedValue);
    }
  }
}class Subject2 implements ISubject {
  List<IObserver> observerList = new ArrayList<>();
  private int myValue;

  public int getMyValue() {
    return myValue;
  }

  public void setMyValue(int myValue) {
    this.myValue = myValue;
    notifyObservers(myValue);
  }

  @Override
  public void register(IObserver o) {
    observerList.add(o);
  }

  @Override
  public void unregister(IObserver o) {
    observerList.remove(o);
  }

  @Override
  public void notifyObservers(int updatedValue ) {
    for (IObserver o : observerList) {
      o.update(this.getClass().getSimpleName(), updatedValue);
    }
  }
}