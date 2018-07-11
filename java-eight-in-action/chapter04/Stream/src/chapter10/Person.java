package chapter10;

import java.util.Optional;

class Insurance {
  private String name;
  public String getName() {
    return name;
  }
}

class Car {
  private Optional<Insurance> insurance;

  public Optional<Insurance> getInsurance() {
    return insurance;
  }
}

public class Person {
  private Optional<Car> car;
  public Optional<Car> getCar() {
    return car;
  }

  /*public String getCarInsuranceName(Person person) {
    if (person == null) {
      return "Unknown";
    }

    Car car = person.getCar();
    if (car == null) {
      return "Unknown";
    }

    Insurance insurance = car.getInsurance();
    if (insurance == null) {
      return "Unknown";
    }
    return insurance.getName();
  }*/

  void example() {
    Optional<Car> optionalCar = Optional.empty();
    Optional<Car> optionalCar1 = Optional.of(new Car());
    Optional<Car> optionalCar2 = Optional.ofNullable(new Car());
  }
}
