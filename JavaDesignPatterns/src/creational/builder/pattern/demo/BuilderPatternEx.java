package creational.builder.pattern.demo;

import java.util.LinkedList;

class Product {
  LinkedList<String> parts;

  Product() {
    parts = new LinkedList<>();
  }

  void add(String part) {
    parts.addLast(part);
  }

  void show() {
    for (String part : parts) {
      System.out.println(part);
    }
  }
}

/**
 * Builders common interface
 */
interface IBuilder {
  void buildBody();
  void insertWheels();
  void addHeadlisghts();
  Product getVehicle();
}

/**
 * Car is ConcreteBuilder
 */
class Car implements IBuilder {
  Product product = new Product();

  @Override
  public void buildBody() {
    product.add("This is a body of a Car");
  }

  @Override
  public void insertWheels() {
    product.add("4 wheels are added");
  }

  @Override
  public void addHeadlisghts() {
    product.add("2 Headlights are added");
  }

  @Override
  public Product getVehicle() {
    return product;
  }
}

/**
 * MotorCycle is a ConcreteBuilder
 */
class MotorCyCle implements IBuilder {
  private Product product = new Product();

  @Override
  public void buildBody() {
    product.add("This is a body of a Motorcycle");
  }

  @Override
  public void insertWheels() {
    product.add("2 wheels are added");
  }

  @Override
  public void addHeadlisghts() {
    product.add("1 headlights are added");
  }

  @Override
  public Product getVehicle() {
    return product;
  }
}

/**
 * Director
 */
class Director {
  IBuilder myBuilder;

  /**
   * A series of steps for the production
   */
  public void construct(IBuilder builder) {
    myBuilder = builder;
    myBuilder.buildBody();
    myBuilder.insertWheels();
    myBuilder.addHeadlisghts();
  }
}

public class BuilderPatternEx {
  public static void main(String[] args) {
    Director director = new Director();

    IBuilder carBuilder = new Car();
    IBuilder motorBuilder = new MotorCyCle();

    // Making Car
    director.construct(carBuilder);
    Product p1 = carBuilder.getVehicle();
    p1.show();

    // Making MotorCycle
    director.construct(motorBuilder);
    Product p2 = motorBuilder.getVehicle();
    p2.show();

  }
}
