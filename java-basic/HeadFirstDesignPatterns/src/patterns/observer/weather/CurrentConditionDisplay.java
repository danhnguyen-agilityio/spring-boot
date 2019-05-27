package patterns.observer.weather;

public class CurrentConditionDisplay implements Observer, DisplayElement {
  private float temperature;
  private float humidity;
  private Subject weatherData;

  public CurrentConditionDisplay(Subject weatherData) {
    this.weatherData = weatherData;
    weatherData.registerObserver(this);
  }

  @Override
  public void display() {
    System.out.println("Current conditions: " + temperature);
    System.out.println("Humidity: " + humidity);
  }

  @Override
  public void update(float temp, float humidity, float pressure) {
    this.temperature = temp;
    this.humidity = humidity;
    display();
  }
}
