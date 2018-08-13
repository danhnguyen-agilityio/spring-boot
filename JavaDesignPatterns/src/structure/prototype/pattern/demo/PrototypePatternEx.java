package structure.prototype.pattern.demo;

import java.util.Random;

abstract class BasicCar implements Cloneable {
  public String modelName;
  public int price;

  public String getModelName() {
    return modelName;
  }

  public void setModelName(String modelName) {
    this.modelName = modelName;
  }

  public static int setPrice() {
    return new Random().nextInt(100000);
  }

  public BasicCar clone() throws CloneNotSupportedException {
    return (BasicCar) super.clone();
  }
}

class Ford extends BasicCar {
  public Ford(String m) {
    modelName = m;
  }

  @Override
  public BasicCar clone() throws CloneNotSupportedException {
    return super.clone();
  }
}

class Nano extends BasicCar {
  public Nano(String m) {
    modelName = m;
  }

  @Override
  public BasicCar clone() throws CloneNotSupportedException {
    return super.clone();
  }
}

public class PrototypePatternEx {
  public static void main(String[] args) throws CloneNotSupportedException{
    BasicCar nano = new Nano("Green Nano");
    nano.price = 100000;

    // Clone nana object
    BasicCar nanoClone = nano.clone();
    nanoClone.price = nanoClone.price + BasicCar.setPrice();
    System.out.println("Original car is: " + nano.modelName + " and price is " + nano.price);
    System.out.println("Clone car is: " + nanoClone.modelName + " and price is " + nanoClone.price);
  }
}
