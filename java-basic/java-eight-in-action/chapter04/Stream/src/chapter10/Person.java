package chapter10;

import java.util.NoSuchElementException;
import java.util.Optional;

class Insurance {
  private String name;

  public Insurance(String name) {
    this.name = name;
  }

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
  private Car car;

  public Optional<Car> getCarAsOptional() {
    return Optional.ofNullable(car);
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

    Insurance insurance = new Insurance("Quality");
    Optional<Insurance> optionalInsurance = Optional.ofNullable(insurance);
    Optional<String> name = optionalInsurance.map(Insurance::getName);

    Optional<Person> optionalPerson = Optional.of(new Person());
  }

  public String getCarInsuranceName(Optional<Person> person) {
    return person.flatMap(Person::getCarAsOptional)
        .flatMap(Car::getInsurance)
        .map(Insurance::getName)
        .orElse("Unknown");

  }

  public Insurance findCheapestInsurance(Person person, Car car) {
    // queries services provided by the different insurance companies
    // compare those data
    return null;
  }

  public Optional<Insurance> nullSafeFindCheapestInsurance(Optional<Person> person, Optional<Car> car) {
//    if (person.isPresent() && car.isPresent()) {
//      return Optional.of(findCheapestInsurance(person.get(), car.get()));
//    } else {
//      return Optional.empty();
//    }
    return person.flatMap(p -> car.map(c -> findCheapestInsurance(p ,c)));
  }

  public static void main(String[] args) {
    Insurance insurance = new Insurance("Quality");
    Optional<Insurance> optionalInsurance = Optional.ofNullable(insurance);
    Optional<Insurance> optionalEmpty = Optional.ofNullable(null);

    System.out.println(optionalInsurance.get().getName());
//    System.out.println(optionalEmpty.get().getName());

    System.out.println(optionalInsurance.orElse(new Insurance("Empty value")).getName());
    System.out.println(optionalEmpty.orElse(new Insurance("Empty value")).getName());

    System.out.println(optionalEmpty.orElseGet(() -> new Insurance("Empty value")).getName());

//    optionalInsurance.orElseThrow(() -> {
//      throw new NoSuchElementException("No have element");
//    }).getName();

    optionalInsurance.ifPresent(insurance1 -> {
      System.out.println(insurance1.getName() + "AAAA");
    });

    optionalInsurance.filter(insurance1 ->  "Qualityy".equals(insurance1.getName()))
        .ifPresent(insurance1 -> {
          System.out.println(insurance1.getName() + "BBBB");
        });
  }
}
