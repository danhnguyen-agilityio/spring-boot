package patterns.observer.weather;

public class WeatherStation {
  public static void main(String[] args) {
    WeatherData weatherData = new WeatherData();

    CurrentConditionDisplay currentConditionDisplay = new CurrentConditionDisplay(weatherData);
    ForecastDisplay forcastDisplay = new ForecastDisplay(weatherData);

    weatherData.setMeasurements(80, 65, 30.4f);
    weatherData.setMeasurements(5, 15, 25.4f);
  }
}
