package patterns.observer.builtin.weather;

import java.util.Observable;
import java.util.Observer;

public class ForecastDisplay implements Observer, DisplayElement {
  private float pressure;
  private Observable observable;

  public ForecastDisplay(Observable observable) {
    this.observable = observable;
    observable.addObserver(this);
  }

  @Override
  public void display() {
    System.out.println("Pressure: " + pressure);
  }

  @Override
  public void update(Observable obs, Object arg) {
    if (obs instanceof WeatherData) {
      WeatherData weatherData = (WeatherData) obs;
      pressure = weatherData.getPressure();
      display();
    }
  }
}
