package observer.pattern.weather;

public class ForecastDisplay implements Observer, DisplayElement {
  private float average;
  private Subject weatherData;

  public ForecastDisplay(Subject weatherData) {
    this.weatherData = weatherData;
    weatherData.registerObserver(this);
  }

  @Override
  public void display() {
    System.out.println("Average: " + average);
  }

  @Override
  public void update(float temp, float humidity, float pressure) {
    this.average = temp / 2;
    display();
  }
}
