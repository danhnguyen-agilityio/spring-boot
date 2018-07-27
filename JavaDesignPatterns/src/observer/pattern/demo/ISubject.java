package observer.pattern.demo;

public interface ISubject {
  void register(IObserver o);
  void unregister(IObserver o);
  void notifyObservers(int i);
}
