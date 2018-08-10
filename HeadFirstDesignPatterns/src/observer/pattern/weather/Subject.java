package observer.pattern.weather;

public interface Subject {
  void registerObserver(Observer o);
  void removeObserver(Observer o);

  /**
   * Notify all observers when the state of Subject has changed
   */
  void notifyObservers();
}
